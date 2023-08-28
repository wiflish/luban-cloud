package com.wiflish.luban.samples.ddd.web.controller;

import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.exception.InvalidParamException;
import java.util.List;

import com.wiflish.luban.samples.ddd.domain.enums.TaskStatusEnum;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.AddTaskCmd;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskStatusCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.wiflish.luban.samples.ddd.constant.ErrorCode.EE_TASK_notExistsStatus;

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
    private TaskService taskService;

    /**
     * 新增任务.
     *
     * @param addTaskCmd
     * @return
     */
    @PostMapping("/task")
    public Response addTask(@RequestBody AddTaskCmd addTaskCmd) {
        return taskService.addTask(addTaskCmd);
    }

    /**
     * 修改任务状态.
     *
     * @param editTaskStatusCmd
     * @return
     */
    @PatchMapping("/task/status")
    public Response editStatus(@RequestBody EditTaskStatusCmd editTaskStatusCmd) {
        Assert.notNull(editTaskStatusCmd.getId(), "id不能为null");
        Assert.notNull(editTaskStatusCmd.getStatus(), "status不能为null");

        Long id = editTaskStatusCmd.getId();
        int status = editTaskStatusCmd.getStatus().intValue();
        if (status == TaskStatusEnum.DOING.getValue()) {
            return taskService.doing(id);
        }
        if (status == TaskStatusEnum.TODO.getValue()) {
            return taskService.todo(id);
        }
        if (status == TaskStatusEnum.DONE.getValue()) {
            return taskService.done(id);
        }

        throw new InvalidParamException(EE_TASK_notExistsStatus.getCode(), EE_TASK_notExistsStatus.getMessage());
    }

    /**
     * 根据条件查询任务.
     *
     * @param query
     * @return
     */
    @GetMapping("/tasks")
    public Response<List<TaskDTO>> getTasks(TaskQuery query) {
        return taskService.pagedTasks(query);
    }

    /**
    * 根据id查询任务.
    *
    * @param taskId
    * @return
    */
    @GetMapping("/tasks/{taskId}")
    public Response<TaskDTO> getTask(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }
}
