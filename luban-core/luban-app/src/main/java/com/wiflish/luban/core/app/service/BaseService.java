package com.wiflish.luban.core.app.service;

import com.wiflish.luban.core.app.assembler.BaseAssembler;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.BaseDTO;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.query.Query;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * @author wiflish
 * @since 2023-08-29
 */
public interface BaseService<T extends BaseDTO, E extends BaseEntity, Q extends Query> {
    BaseAssembler<T, E> getAssembler();

    BaseRepository<E> getRepository();

    default Long save(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        return getRepository().save(entity);
    }

    default void remove(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        getRepository().remove(entity);
    }

    default void remove(@NotNull Long id) {
        getRepository().remove(id);
    }

    default T get(@NotNull Long id) {
        E entity = getRepository().find(id);
        return getAssembler().toDTO(entity);
    }

    default T get(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);

        E fromDB = getRepository().find(entity);

        return getAssembler().toDTO(fromDB);
    }

    default Response<List<T>> pageList(@NotNull Q query, Pager pager) {
        E fromDB = getRepository().pageList(query);
        return getAssembler().toDTO(fromDB);
    }
}
