package com.wiflish.luban.samples.ddd.service;

import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.dto.Command;
import com.wiflish.luban.core.dto.DTO;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.service.BaseService;

/**
 * 任务服务.
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface TaskService<C extends Command, Q extends Query, T extends DTO, E extends Entity> extends BaseService<C, Q, T, E> {
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
}