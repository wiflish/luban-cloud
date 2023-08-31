package com.wiflish.luban.samples.app.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wiflish.luban.core.mybatis.query.LambdaQueryWrapperFactory;
import com.wiflish.luban.samples.infra.po.TaskPO;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import org.springframework.stereotype.Component;

/**
 * @author wiflish
 * @since 2023-08-31
 */
@Component
public class TaskLambdaQueryWrapperFactory implements LambdaQueryWrapperFactory<TaskPO, TaskQuery> {
    @Override
    public String getWrapperId() {
        return TaskQuery.class.getName();
    }

    public LambdaQueryWrapper<TaskPO> getLambdaQueryWrapper(TaskQuery query) {
        LambdaQueryWrapper<TaskPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.likeRight(query.getTaskName() != null, TaskPO::getName, query.getTaskName());
        lambdaQueryWrapper.eq(query.getStatus() != null, TaskPO::getStatus, query.getStatus());

        return lambdaQueryWrapper;
    }
}
