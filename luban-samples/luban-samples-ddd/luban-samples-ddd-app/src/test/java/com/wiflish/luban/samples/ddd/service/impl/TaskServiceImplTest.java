package com.wiflish.luban.samples.ddd.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.AddTaskCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.BaseTests;
import com.wiflish.luban.samples.ddd.service.TaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public class TaskServiceImplTest extends BaseTests {
    @Autowired
    private TaskService taskService;

    @Test
    public void addTask() {
        AddTaskCmd addTaskCmd = new AddTaskCmd();
        addTaskCmd.setName("测试用例评审Review");
        Response response = taskService.addTask(addTaskCmd);

        assertTrue(response.isSuccess());
    }

    @Test
    public void getTasks() {
        for (int i = 0; i < 10; i++) {
            AddTaskCmd addTaskCmd = buildAddTaskCmd("测试");
            taskService.addTask(addTaskCmd);
        }
        TaskQuery taskQuery = new TaskQuery();
        taskQuery.setTaskName("测试");

        PageResponse<TaskDTO> tasks = taskService.pagedTasks(taskQuery);

        assertTrue(tasks.isSuccess());
        assertEquals(10, tasks.getData().size());

        taskQuery.setTaskName("测试");
        taskQuery.setStatus(1);
        tasks = taskService.pagedTasks(taskQuery);

        assertTrue(tasks.isSuccess());
        assertEquals(10, tasks.getData().size());

        taskQuery.setStatus(2);
        tasks = taskService.pagedTasks(taskQuery);

        assertTrue(tasks.isSuccess());
        assertEquals(0, tasks.getData().size());
    }

    AddTaskCmd buildAddTaskCmd(String prefix) {
        String suffix = RandomUtil.randomNumbers(5);
        AddTaskCmd addTaskCmd = new AddTaskCmd();
        addTaskCmd.setName(prefix + "_" + suffix);
        return addTaskCmd;
    }
}