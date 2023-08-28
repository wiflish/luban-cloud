package com.wiflish.luban.core.domain.repository;

import com.wiflish.luban.core.domain.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;

/**
 * BaseRepository, 在Repository中，有两个约束：<br>
 * <p>1. 所有方法的回参，只能是Entity相关，不能出现DO，或者DTO</p>
 * <p>2. 查询方法的入参，可以是Entity，DTO</p>
 *
 * @author wiflish
 * @since 2023-08-28
 */
public interface BaseRepository<E extends BaseEntity> {
    /**
     * 保存一个entity
     */
    default Long save(@NotNull E entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 移除一个entity
     *
     * @param entity entity需要有id.
     */
    default void remove(@NotNull E entity) {
        throw new UnsupportedOperationException();
    }

    /**
     * 移除一个entity
     */
    default void remove(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * 通过ID查找entity。
     */
    default E find(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }
}
