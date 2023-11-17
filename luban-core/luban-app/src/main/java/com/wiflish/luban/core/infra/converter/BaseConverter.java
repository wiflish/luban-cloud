package com.wiflish.luban.core.infra.converter;

import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.po.BasePO;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public interface BaseConverter<Q extends Query, E extends Entity, PO extends BasePO> {
    PO toPO(E entity);

    PO toPO(Q query);

    E toEntity(PO po);
}
