package com.wiflish.luban.samples.app.service.impl;

import com.wiflish.luban.core.app.assembler.BaseAssembler;
import com.wiflish.luban.core.app.service.BaseService;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.OneResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.app.assembler.TaskAssembler;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.repository.TaskRepository;
import com.wiflish.luban.samples.ddd.domain.service.TaskSyncDomainService;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务服务
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Slf4j
@Service
public class TaskServiceImpl extends BaseService<EditTaskCmd, TaskDTO, Task, TaskQuery> implements TaskService {
    @Autowired
    private TaskSyncDomainService taskSyncDomainService;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskAssembler taskAssembler;

    @Override
    public BaseAssembler<EditTaskCmd, TaskDTO, Task> getAssembler() {
        return this.taskAssembler;
    }

    @Override
    public BaseRepository<Task, TaskQuery> getRepository() {
        return this.taskRepository;
    }

    @Override
    public OneResponse<Long> addTask(EditTaskCmd editTaskCmd) {
        return save(editTaskCmd);
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
    public ListResponse<TaskDTO> pagedTasks(TaskQuery query) {
        return pageList(query, new Pager());
    }

    @Override
    public OneResponse<TaskDTO> getTaskById(Long taskId) {
        return get(taskId);
    }
}
