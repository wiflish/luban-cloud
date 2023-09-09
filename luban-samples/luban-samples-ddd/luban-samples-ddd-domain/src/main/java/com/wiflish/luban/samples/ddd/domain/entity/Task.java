package com.wiflish.luban.samples.ddd.domain.entity;

import cn.hutool.core.lang.Assert;
import cn.hutool.extra.spring.SpringUtil;
import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.samples.ddd.domain.enums.TaskStatusEnum;
import com.wiflish.luban.samples.ddd.domain.repository.TaskRepository;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 任务实体. 只包含与任务有关的属性和行为定义，与数据库操作相关的操作放到infra层。
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Setter
@Getter
public class Task extends Entity {
    @Serial
    private static final long serialVersionUID = 1L;

    private TaskRepository taskRepository = SpringUtil.getBean(TaskRepository.class);

    /**
     * 任务名称.
     */
    private String name;

    /**
     * 任务描述.
     */
    private String remark;

    /**
     * 任务状态. 1=待开始; 2=进行中; 3=已完成
     */
    private Integer status;

    public void done() {
        Assert.notNull(this.getId(), "id不能为null");
        this.status = TaskStatusEnum.DONE.getValue();
        taskRepository.save(this);
    }

    public void doing() {
        Assert.notNull(this.getId(), "id不能为null");
        this.status = TaskStatusEnum.DOING.getValue();
        taskRepository.save(this);
    }

    public void todo() {
        this.status = TaskStatusEnum.TODO.getValue();
        if (this.getId() == null) {
            Long returnId = taskRepository.save(this);
            this.setId(returnId);
        } else {
            taskRepository.save(this);
        }
    }
}
