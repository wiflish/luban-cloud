package com.wiflish.luban.samples.ddd.service;

import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.ddd.dto.TaskDTO;
import com.wiflish.luban.samples.ddd.dto.cmd.AddTaskCmd;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;

import java.util.List;

/**
 * 任务服务.
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface TaskService {

    /**
     * 新增任务。
     *
     * @param addTaskCmd
     * @return
     */
    Response addTask(AddTaskCmd addTaskCmd);

    /**
     * 待做。
     *
     * @param taskId
     * @return
     */
    Response todo(Long taskId);

    /**
     * 进行中。
     *
     * @param taskId
     * @return
     */
    Response doing(Long taskId);

    /**
     * 已完成。
     *
     * @param taskId
     * @return
     */
    Response done(Long taskId);

    /**
     * 根据名称模糊查询.
     *
     * @param query
     * @return
     */
    Response<List<TaskDTO>> pagedTasks(TaskQuery query);

    /**
    * 根据id查询任务.
    *
    * @param taskId
    * @return
    */
    Response<TaskDTO> getTaskById(Long taskId);
}