package com.wiflish.luban.core.app.service;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.dto.*;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.service.BaseService;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 *
 * @author wiflish
 * @since 2023-08-29
 */
public abstract class BaseServiceImpl<C extends Command, T extends BaseDTO, Q extends Query, E extends BaseEntity> implements BaseService<C, T, Q, E> {
    @Override
    public OneResponse<Long> save(@NotNull C cmd) {
        E entity = getAssembler().toEntity(cmd);
        return OneResponse.of(getRepository().save(entity));
    }

    @Override
    public void remove(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        getRepository().remove(entity);
    }

    @Override
    public void remove(@NotNull Long id) {
        getRepository().remove(id);
    }

    @Override
    public OneResponse<T> get(@NotNull Long id) {
        E entity = getRepository().find(id);

        return OneResponse.of(getAssembler().toDTO(entity));
    }

    @Override
    public OneResponse<T> get(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        E fromDB = getRepository().find(entity);

        return OneResponse.of(getAssembler().toDTO(fromDB));
    }

    @Override
    public ListResponse<T> pageList(@NotNull Q query, Pager pager) {
        ListResponse<E> fromDB = getRepository().listPage(query, pager);
        List<T> result = fromDB.getData().stream().map(e -> getAssembler().toDTO(e)).toList();

        return ListResponse.of(result, fromDB.getTotal(), fromDB.getPage(), fromDB.getSize());
    }
}
