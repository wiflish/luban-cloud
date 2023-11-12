package com.wiflish.luban.samples.ddd.infra.converter;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.infra.po.TaskPO;
import org.springframework.stereotype.Component;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Component
public class TaskConverter extends BaseConverter<TaskQuery, Task, TaskPO> {
    @Override
    public TaskPO toPO(Task entity) {
        TaskPO taskPO = new TaskPO();
        BeanUtil.copyProperties(entity, taskPO);

        return taskPO;
    }

    @Override
    public TaskPO toPO(TaskQuery query) {
        TaskPO taskPO = new TaskPO();
        BeanUtil.copyProperties(query, taskPO);

        return taskPO;
    }

    @Override
    public Task toEntity(TaskPO po) {
        Task task = new Task();
        BeanUtil.copyProperties(po, task);
        return task;
    }
}
