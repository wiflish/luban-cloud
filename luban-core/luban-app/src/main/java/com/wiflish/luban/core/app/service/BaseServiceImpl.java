package com.wiflish.luban.core.app.service;

import com.wiflish.luban.core.domain.entity.Entity;
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
public abstract class BaseServiceImpl<C extends Command, Q extends Query, T extends DTO, E extends Entity> implements BaseService<C, Q, T, E> {
    @Override
    public OneResponse<Long> save(@NotNull C cmd) {
        E entity = getAssembler().toEntity(cmd);
        Long id = getRepository().save(entity);
        cmd.setId(id);
        return OneResponse.of(id);
    }

    @Override
    public OneResponse<Integer> remove(@NotNull Long id) {
        return OneResponse.of(getRepository().delete(id));
    }

    @Override
    public OneResponse<Integer> remove(List<Long> ids) {
        return OneResponse.of(getRepository().delete(ids));
    }

    @Override
    public OneResponse<T> get(@NotNull Long id) {
        E entity = getRepository().find(id);

        return OneResponse.of(getAssembler().toDTO(entity));
    }

    @Override
    public OneResponse<T> get(@NotNull Q query) {
        E fromDB = getRepository().find(query);

        return OneResponse.of(getAssembler().toDTO(fromDB));
    }

    @Override
    public ListResponse<T> listPage(@NotNull Q query, Pager pager) {
        ListResponse<E> fromDB = getRepository().listPage(query, pager);
        List<T> result = fromDB.getData().stream().map(e -> getAssembler().toDTO(e)).toList();

        return ListResponse.of(result, fromDB.getTotal(), fromDB.getPage(), fromDB.getSize());
    }

    @Override
    public ListResponse<T> listAll(@NotNull Q query) {
        List<E> fromDB = getRepository().listAll(query);
        List<T> result = fromDB.stream().map(e -> getAssembler().toDTO(e)).toList();

        return ListResponse.of(result);
    }
}
