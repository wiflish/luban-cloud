package com.wiflish.luban.core.infra.converter;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.infra.po.BasePO;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public abstract class BaseConverter<E extends BaseEntity, PO extends BasePO> {

    public abstract PO toPO(E entity);

    public abstract E toEntity(PO persistentObject);
}
