package com.wiflish.luban.samples.ddd.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.repository.TaskRepository;
import com.wiflish.luban.samples.ddd.domain.service.TaskSyncDomainService;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.AddTaskCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.infra.converter.TaskConverter;
import com.wiflish.luban.samples.ddd.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 任务服务
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskSyncDomainService taskSyncDomainService;
    @Autowired
    private TaskRepository taskRepository;
    @Override
    public Response addTask(AddTaskCmd addTaskCmd) {
        Task task = BeanUtil.copyProperties(addTaskCmd, Task.class);
        task.todo();
        return Response.of(task.getId());
    }

    @Override
    public Response todo(Long taskId) {
        Task task = taskRepository.find(taskId);
        task.todo();
        return Response.of();
    }

    @Override
    public Response doing(Long taskId) {
        Task task = taskRepository.find(taskId);
        task.doing();
        return Response.of();
    }

    @Override
    public Response done(Long taskId) {
        Task task = taskRepository.find(taskId);
        task.done();
        return Response.of();
    }

    @Override
    public Response<List<TaskDTO>> pagedTasks(TaskQuery query) {
        Response<List<Task>> tasks = taskRepository.findTasks(query);
        return null;
    }

    @Override
    public Response<TaskDTO> getTaskById(Long taskId) {
        Task task = taskRepository.find(taskId);
        TaskDTO taskDTO = TaskConverter.toTaskDTO(task);

        return Response.of(taskDTO);
    }
}
