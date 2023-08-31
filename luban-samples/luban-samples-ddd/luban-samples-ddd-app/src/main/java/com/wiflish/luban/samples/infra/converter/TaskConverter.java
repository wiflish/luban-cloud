package com.wiflish.luban.samples.infra.converter;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.infra.po.TaskPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Component
@Scope("prototype")
public class TaskConverter extends BaseConverter<Task, TaskPO> {
    @Autowired
    private Task task;

    @Override
    public TaskPO toPO(Task entity) {
        TaskPO taskPO = new TaskPO();
        BeanUtil.copyProperties(entity, taskPO);
        return taskPO;
    }

    @Override
    public Task toEntity(TaskPO po) {
        BeanUtil.copyProperties(po, task);
        return task;
    }
}
