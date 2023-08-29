package com.wiflish.luban.core.infra.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.infra.po.BasePO;

import java.util.List;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public abstract class BaseMybatisRepositoryImpl<E extends BaseEntity, PO extends BasePO, Q extends Query> implements BaseRepository<E, Q> {
    protected abstract BaseMapper<PO> getMapper();

    protected abstract BaseConverter<E, PO> getConverter();

    @Override
    public Long save(E entity) {
        boolean newEntity = (entity.getId() == null);
        PO po = getConverter().toPO(entity);
        if (newEntity) {
            getMapper().insert(po);
        } else {
            getMapper().updateById(po);
        }
        return po.getId();
    }

    @Override
    public void remove(Long id) {
        getMapper().deleteById(id);
    }

    @Override
    public E find(Long id) {
        PO po = getMapper().selectById(id);
        return getConverter().toEntity(po);
    }

    @Override
    public E find(E entity) {
        return null;
    }

    @Override
    public List<E> listAll(Q query) {
        return null;
    }

    @Override
    public Response<List<E>> listPage(Q query, Pager pager) {
        return null;
    }
}
