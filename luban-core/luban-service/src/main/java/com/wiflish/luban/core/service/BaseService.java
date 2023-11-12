package com.wiflish.luban.core.service;

import com.wiflish.luban.core.assembler.Assembler;
import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.*;
import com.wiflish.luban.core.dto.query.Query;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Base Service
 *
 * @author wiflish
 * @since 2023-09-04
 */
public interface BaseService<C extends Command, Q extends Query, T extends DTO, E extends Entity> {
    /**
     * DTO to Entity assembler
     * @return
     */
    Assembler<C, T, E> getAssembler();

    /**
     * Repository
     *
     * @return
     */
    BaseRepository<Q, E> getRepository();

    /**
     * save or update entity
     * @param cmd
     * @return id
     */
    OneResponse<Long> save(C cmd);

    /**
     * remove by entity id
     * @param id
     */
    OneResponse<Integer> remove(@NotNull Long id);

    /**
     * batch remove by entity id
     * @param ids
     */
    OneResponse<Integer> remove(@NotNull List<Long> ids);

    /**
     * Get by entity id
     *
     * @param id
     * @return
     */
    OneResponse<T> get(@NotNull Long id);

    /**
     * Get One Result By Condition
     *
     * @param query
     * @return
     */
    OneResponse<T> get(Q query);

    /**
     *
     * @param query
     * @param pager
     * @return
     */
    ListResponse<T> listPage(Q query, Pager pager);

    /**
     *
     * @param query
     * @return
     */
    ListResponse<T> listAll(Q query);
}
