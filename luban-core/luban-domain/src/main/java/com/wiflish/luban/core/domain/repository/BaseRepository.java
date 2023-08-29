package com.wiflish.luban.core.domain.repository;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.query.Query;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * BaseRepository, 在Repository中，有两个约束：<br>
 * <p>1. 所有方法的回参，只能是Entity相关，不能出现DO，或者DTO</p>
 * <p>2. 查询方法的入参，可以是Entity，DTO</p>
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface BaseRepository<E extends BaseEntity, Q extends Query> {
    /**
     * 保存/更新一个entity, 当Entity的id不为空时，更新。
     */
    Long save(@NotNull E entity);

    /**
     * 移除一个entity
     */
    void remove(@NotNull Long id);

    /**
     * 移除一个entity
     *
     * @param entity entity需要有id.
     */
    default void remove(@NotNull E entity) {
        Long id = entity.getId();
        if (id == null) {
            return;
        }
        remove(id);
    }

    /**
     * 通过ID查找entity。
     */
    E find(@NotNull Long id);

    E find(@NotNull E entity);

    /**
     * 返回符合条件的所有记录，默认限制最大条数2000.
     *
     * @param query
     * @return
     */
    List<E> listAll(@NotNull Q query);

    Response<List<E>> listPage(@NotNull Q query, Pager pager);
}
