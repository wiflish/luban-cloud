package com.wiflish.luban.core.app.web.controller;

import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.dto.Command;
import com.wiflish.luban.core.dto.DTO;
import com.wiflish.luban.core.dto.query.Query;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public abstract class BaseController<C extends Command, T extends DTO, Q extends Query, E extends Entity> {
//    public abstract CommandService<C, T, E> getCommandService();
}
