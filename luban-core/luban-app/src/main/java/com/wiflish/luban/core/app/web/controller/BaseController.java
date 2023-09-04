package com.wiflish.luban.core.app.web.controller;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.dto.BaseDTO;
import com.wiflish.luban.core.dto.Command;
import com.wiflish.luban.core.dto.query.Query;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public abstract class BaseController<C extends Command, T extends BaseDTO, E extends BaseEntity, Q extends Query> {
//    public abstract BaseService<T, E, Q> getService();

}
