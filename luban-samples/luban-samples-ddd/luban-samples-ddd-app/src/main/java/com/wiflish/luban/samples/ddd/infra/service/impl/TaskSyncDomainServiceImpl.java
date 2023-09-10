package com.wiflish.luban.samples.ddd.infra.service.impl;

import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.ddd.domain.entity.Task;
import com.wiflish.luban.samples.ddd.domain.service.TaskSyncDomainService;
import org.springframework.stereotype.Component;

/**
 * 任务同步到第三方服务.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Component
public class TaskSyncDomainServiceImpl implements TaskSyncDomainService {
    @Override
    public Response syncTask(Task task) {
        return null;
    }
}
