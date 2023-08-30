package com.wiflish.luban.samples.ddd.infra.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.mybatis.repository.impl.BaseMybatisRepositoryImpl;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.repository.TaskRepository;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.infra.po.TaskPO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Repository
public class TaskRepositoryImpl extends BaseMybatisRepositoryImpl<Task, TaskPO> implements TaskRepository {

    @Override
    protected BaseMapper<TaskPO> getMapper() {
        return null;
    }

    @Override
    protected BaseConverter getConverter() {
        return null;
    }

    @Override
    public Response<List<Task>> findTasks(TaskQuery taskQuery) {
        return null;
    }
}