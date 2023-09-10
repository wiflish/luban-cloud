package com.wiflish.luban.samples.ddd.app.assembler;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.core.assembler.Assembler;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskCmd;
import org.springframework.stereotype.Component;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Component
public class TaskAssembler implements Assembler<EditTaskCmd, TaskDTO, Task> {
    @Override
    public Task toEntity(TaskDTO dto) {
        Task task = new Task();
        BeanUtil.copyProperties(dto, task);
        return task;
    }

    @Override
    public Task toEntity(EditTaskCmd cmd) {
        Task task = new Task();
        BeanUtil.copyProperties(cmd, task);
        return task;
    }

    @Override
    public TaskDTO toDTO(Task entity) {
        TaskDTO taskDTO = new TaskDTO();
        BeanUtil.copyProperties(entity, taskDTO);
        return taskDTO;
    }
}
