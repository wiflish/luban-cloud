package com.wiflish.luban.starter.mybatis.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.core.infra.util.PageUtil;
import com.wiflish.luban.starter.mybatis.query.QueryFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wiflish.luban.core.dto.constant.BaseConstant.DEFAULT_PAGE_NO;
import static com.wiflish.luban.core.dto.constant.BaseConstant.DEFAULT_PAGE_SIZE_MAX;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Slf4j
@Component
public abstract class BaseMybatisRepositoryImpl<Q extends Query, E extends Entity, PO extends BasePO> implements BaseRepository<Q, E> {
    protected abstract BaseMapper<PO> getMapper();

    protected abstract BaseConverter<Q, E, PO> getConverter();

    protected abstract QueryFunction<Q, PO> getQueryFunction();

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
    public Integer delete(Long id) {
        return getMapper().deleteById(id);
    }

    @Override
    public Integer delete(List<Long> ids) {
        return getMapper().deleteBatchIds(ids);
    }

    @Override
    public E find(Long id) {
        PO po = getMapper().selectById(id);
        return getConverter().toEntity(po);
    }

    @Override
    public E find(Q query) {
        LambdaQueryWrapper<PO> lambdaQueryWrapper = getQueryFunction().apply(query);

        //只查询最新更新的一条记录.
        lambdaQueryWrapper.last(" limit 1 ");

        PO fromDB = getMapper().selectOne(lambdaQueryWrapper);
        return getConverter().toEntity(fromDB);
    }

    @Override
    public List<E> listAll(Q query) {
        Pager pager = new Pager();
        pager.setPage(DEFAULT_PAGE_NO);
        pager.setSize(DEFAULT_PAGE_SIZE_MAX);

        ListResponse<E> result = listPage(query, pager);
        return result.getData();
    }

    @Override
    public ListResponse<E> listPage(Q query, Pager pager) {
        LambdaQueryWrapper<PO> lambdaQueryWrapper = getQueryFunction().apply(query);
        Page<PO> page = PageUtil.toMybatisPlusPage(pager);

        Page<PO> pageFromDB = getMapper().selectPage(page, lambdaQueryWrapper);
        List<E> entities = pageFromDB.getRecords().stream().map(po -> getConverter().toEntity(po)).toList();

        return ListResponse.of(entities, pageFromDB.getTotal(), page.getCurrent(), page.getSize());
    }
}
