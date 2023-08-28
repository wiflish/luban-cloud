package com.wiflish.luban.core.infra.repository.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.infra.po.BasePO;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public abstract class BaseMybatisRepositoryImpl<E extends BaseEntity, PO extends BasePO> implements BaseRepository<E> {
    protected abstract BaseMapper<PO> getMapper();

    protected abstract BaseConverter getConverter();

    @Override
    public Long save(BaseEntity entity) {
        boolean newEntity = (entity.getId() == null);
        PO po = (PO) getConverter().toPO(entity);
        if (newEntity) {
            getMapper().insert(po);
        } else {
            getMapper().updateById(po);
        }
        return po.getId();
    }

    @Override
    public void remove(BaseEntity entity) {
        getMapper().deleteById(entity.getId());
    }

    @Override
    public void remove(Long id) {
        getMapper().deleteById(id);
    }

    @Override
    public E find(Long id) {
        PO po = getMapper().selectById(id);
        return (E) getConverter().toEntity(po);
    }
}
