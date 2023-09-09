package com.wiflish.luban.samples.ddd.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.OneResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.samples.ddd.BaseTests;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.enums.TaskStatusEnum;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.EditTaskCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public class TaskServiceImplTest extends BaseTests {
    @Autowired
    private TaskService<EditTaskCmd, TaskQuery, TaskDTO, Task> taskService;

    @Test
    public void addTask() {
        EditTaskCmd addTaskCmd = new EditTaskCmd();
        addTaskCmd.setName("测试用例评审Review");
        OneResponse<Long> response = taskService.save(addTaskCmd);
        assertEquals("0", response.getCode());

        taskService.doing(response.getData());
        OneResponse<TaskDTO> result = taskService.get(response.getData());
        assertNotNull(result);
        assertEquals(TaskStatusEnum.DOING.getValue(), result.getData().getStatus());

        taskService.done(response.getData());
        result = taskService.get(response.getData());
        assertNotNull(result);
        assertEquals(TaskStatusEnum.DONE.getValue(), result.getData().getStatus());

        taskService.remove(response.getData());
        result = taskService.get(response.getData());
        assertNotNull(result);
        assertNull(result.getData().getName());
    }

    @Test
    public void getTasks() {
        for (int i = 0; i < 10; i++) {
            EditTaskCmd addTaskCmd = buildAddTaskCmd("测试");
            taskService.save(addTaskCmd);
        }
        TaskQuery taskQuery = new TaskQuery();
        taskQuery.setTaskName("测试");

        ListResponse<TaskDTO> tasks = taskService.listAll(taskQuery);

        assertNotNull(tasks);
        assertEquals(10, tasks.getData().size());

        taskQuery.setTaskName("测试");
        taskQuery.setStatus(1);
        tasks = taskService.listAll(taskQuery);
        assertNotNull(tasks);
        assertEquals(10, tasks.getData().size());

        taskQuery.setStatus(2);
        tasks = taskService.listAll(taskQuery);
        assertNotNull(tasks);
        assertEquals(0, tasks.getData().size());


        Pager pager = new Pager(1, 1);
        TaskQuery taskQueryPage = new TaskQuery();
        taskQueryPage.setTaskName("测试");
        ListResponse<TaskDTO> taskDTOListResponse = taskService.listPage(taskQueryPage, pager);
        assertNotNull(taskDTOListResponse);
        assertEquals(0, taskDTOListResponse.getTotal().longValue());
        assertEquals(1, taskDTOListResponse.getData().size());

        pager = new Pager(10, 1, true);
        taskQueryPage = new TaskQuery();
        taskQueryPage.setTaskName("测试");
        taskDTOListResponse = taskService.listPage(taskQueryPage, pager);
        assertNotNull(taskDTOListResponse);
        assertEquals(10, taskDTOListResponse.getTotal().longValue());
        assertEquals(1, taskDTOListResponse.getData().size());
    }

    EditTaskCmd buildAddTaskCmd(String prefix) {
        String suffix = RandomUtil.randomNumbers(5);
        EditTaskCmd addTaskCmd = new EditTaskCmd();
        addTaskCmd.setName(prefix + "_" + suffix);
        return addTaskCmd;
    }
}