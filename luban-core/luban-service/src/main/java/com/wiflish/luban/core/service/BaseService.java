package com.wiflish.luban.core.service;

import com.wiflish.luban.core.assembler.BaseAssembler;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.*;
import com.wiflish.luban.core.dto.query.Query;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author wiflish
 * @since 2023-09-04
 */
public interface BaseService<C extends Command, T extends BaseDTO, Q extends Query, E extends BaseEntity> {
    /**
     * DTO to Entity assembler
     * @return
     */
    BaseAssembler<C, T, E> getAssembler();

    /**
     * Repository
     *
     * @return
     */
    BaseRepository<E, Q> getRepository();

    /**
     * save or update entity
     * @param cmd
     * @return
     */
    OneResponse<Long> save(C cmd);

    /**
     * remove by entity id
     * @param dto
     */
    void remove(T dto);

    /**
     * remove by entity id
     * @param id
     */

    void remove(@NotNull Long id);

    /**
     *
     * @param id
     * @return
     */
    OneResponse<T> get(@NotNull Long id);

    /**
     *
     * @param dto
     * @return
     */
    OneResponse<T> get(T dto);

    /**
     *
     * @param query
     * @param pager
     * @return
     */
    ListResponse<T> pageList(Q query, Pager pager);
}
