package com.wiflish.luban.samples.ddd.domain.service;

import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.samples.ddd.domain.entity.Task;

/**
 * 任务的领域服务. 把任务同步发送给第三方.
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface TaskSyncDomainService {
    /**
     * 同步任务.
     *
     * @param task
     * @return
     */
    Response syncTask(Task task);
}
