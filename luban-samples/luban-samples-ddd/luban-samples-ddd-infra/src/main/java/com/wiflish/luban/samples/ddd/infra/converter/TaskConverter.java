package com.wiflish.luban.samples.ddd.infra.converter;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.infra.po.TaskPO;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public class TaskConverter {
    public static TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        BeanUtil.copyProperties(task, taskDTO);
        return taskDTO;
    }

    public static TaskPO toTaskPO(Task task) {
        TaskPO taskPO = new TaskPO();
        BeanUtil.copyProperties(task, taskPO);
        return taskPO;
    }

    public static Task toTask(TaskPO taskPO) {
        Task task = new Task();
        BeanUtil.copyProperties(taskPO, task);
        return task;
    }
}
