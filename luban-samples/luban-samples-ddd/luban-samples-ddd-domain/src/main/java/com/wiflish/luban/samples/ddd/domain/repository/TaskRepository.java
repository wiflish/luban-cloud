package com.wiflish.luban.samples.ddd.domain.repository;

import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;

/**
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface TaskRepository extends BaseRepository<Task, TaskQuery> {
    ListResponse<Task> findTasks(TaskQuery taskQuery);
}
