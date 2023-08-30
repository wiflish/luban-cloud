package com.wiflish.luban.samples.ddd.infra;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.wiflish.luban.core.mybatis.query.QueryMapping;
import com.wiflish.luban.samples.ddd.dto.query.TaskQuery;
import com.wiflish.luban.samples.ddd.infra.po.TaskPO;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wiflish
 * @since 2023-08-30
 */
public class TaskQueryWrapper {
    private TaskQuery taskQuery;

    public Map<String, QueryMapping> getMapping() {
        Map<String, QueryMapping> mapping = new HashMap<>();
        TaskPO taskPO = new TaskPO();
        String taskName = taskQuery.getTaskName();
        if (StrUtil.isNotEmpty(taskName)) {
            SFunction<TaskPO, String> task = TaskPO::getName;
            QueryMapping query = new QueryMapping();
            query.setPropertyName("taskName");
            query.setSFunction(task);
            query.setSqlKeyword(SqlKeyword.EQ);

            mapping.put("taskName", query);
        }

        return mapping;
    }

}
