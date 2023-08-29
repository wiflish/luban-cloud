package com.wiflish.luban.core.app.web.controller;

import com.wiflish.luban.core.app.service.BaseService;
import com.wiflish.luban.core.dto.BaseDTO;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public abstract class BaseController<T extends BaseDTO> {
    public abstract BaseService<T> getService();
}
