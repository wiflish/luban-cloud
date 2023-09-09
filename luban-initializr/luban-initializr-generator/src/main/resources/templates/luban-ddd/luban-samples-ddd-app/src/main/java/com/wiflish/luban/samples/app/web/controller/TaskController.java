package com.wiflish.luban.samples.app.web.controller;

import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.OneResponse;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.exception.BizException;
import com.wiflish.luban.samples.ddd.constant.SamplesErrorCodeConstant;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.enums.TaskStatusEnum;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskCmd;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskStatusCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 任务控制器.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@RestController
@RequestMapping("/sample/ddd")
public class TaskController {
    @Autowired
    private TaskService<EditTaskCmd, TaskQuery, TaskDTO, Task> taskService;

    /**
     * 新增任务.
     *
     * @param addTaskCmd
     * @return
     */
    @PostMapping("/task")
    public Response addTask(@RequestBody EditTaskCmd addTaskCmd) {
        return taskService.save(addTaskCmd);
    }

    /**
     * 修改任务状态.
     *
     * @param editTaskStatusCmd
     * @return
     */
    @PutMapping("/task/status")
    public Response editStatus(@Valid @RequestBody EditTaskStatusCmd editTaskStatusCmd) {
        Long id = editTaskStatusCmd.getId();
        int status = editTaskStatusCmd.getStatus();
        if (status == TaskStatusEnum.DOING.getValue()) {
            return taskService.doing(id);
        }
        if (status == TaskStatusEnum.TODO.getValue()) {
            return taskService.todo(id);
        }
        if (status == TaskStatusEnum.DONE.getValue()) {
            return taskService.done(id);
        }

        throw new BizException(SamplesErrorCodeConstant.SAMPLE_TASK_STATUS_CODE);
    }

    /**
     * 根据条件查询任务.
     *
     * @param query
     * @return
     */
    @GetMapping("/tasks")
    public ListResponse<TaskDTO> getTasks(TaskQuery query) {
        return taskService.listAll(query);
    }

    /**
    * 根据id查询任务.
    *
    * @param taskId
    * @return
    */
    @GetMapping("/tasks/{taskId}")
    public OneResponse<TaskDTO> getTask(@PathVariable Long taskId) {
        return taskService.get(taskId);
    }
}
