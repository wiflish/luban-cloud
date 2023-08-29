package com.wiflish.luban.core.app.assembler;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.dto.BaseDTO;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public interface BaseAssembler<T extends BaseDTO, E extends BaseEntity> {
    E toEntity(T dto);

    T toDTO(E entity);
}
