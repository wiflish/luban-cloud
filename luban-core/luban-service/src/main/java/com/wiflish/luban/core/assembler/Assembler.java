package com.wiflish.luban.core.assembler;

import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.dto.DTO;
import com.wiflish.luban.core.dto.Command;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public interface Assembler<C extends Command, T extends DTO, E extends Entity> {
    E toEntity(T dto);
    
    E toEntity(C cmd);

    T toDTO(E entity);
}
