package com.wiflish.luban.samples.app.assembler;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.core.app.assembler.BaseAssembler;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskCmd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Component
@Scope("prototype")
public class TaskAssembler implements BaseAssembler<EditTaskCmd, TaskDTO, Task> {
    @Autowired
    private Task task;

    @Override
    public Task toEntity(TaskDTO dto) {
        BeanUtil.copyProperties(dto, task);
        return task;
    }

    @Override
    public Task toEntity(EditTaskCmd cmd) {
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
