package com.wiflish.luban.core.app.service;

import com.wiflish.luban.core.app.assembler.BaseAssembler;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.*;
import com.wiflish.luban.core.dto.query.Query;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 *
 * @author wiflish
 * @since 2023-08-29
 */
public abstract class BaseService<C extends Command, T extends BaseDTO, E extends BaseEntity, Q extends Query> {
    public abstract BaseAssembler<C, T, E> getAssembler();

    public abstract BaseRepository<E, Q> getRepository();

    public OneResponse<Long> save(@NotNull C cmd) {
        E entity = getAssembler().toEntity(cmd);
        return OneResponse.of(getRepository().save(entity));
    }

    public void remove(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        getRepository().remove(entity);
    }

    public void remove(@NotNull Long id) {
        getRepository().remove(id);
    }

    public OneResponse<T> get(@NotNull Long id) {
        E entity = getRepository().find(id);

        return OneResponse.of(getAssembler().toDTO(entity));
    }

    public OneResponse<T> get(@NotNull T dto) {
        E entity = getAssembler().toEntity(dto);
        E fromDB = getRepository().find(entity);

        return OneResponse.of(getAssembler().toDTO(fromDB));
    }

    public ListResponse<T> pageList(@NotNull Q query, Pager pager) {
        ListResponse<E> fromDB = getRepository().listPage(query, pager);
        List<T> result = fromDB.getData().stream().map(e -> getAssembler().toDTO(e)).toList();

        return ListResponse.of(result, fromDB.getTotal(), fromDB.getPage(), fromDB.getSize());
    }
}
