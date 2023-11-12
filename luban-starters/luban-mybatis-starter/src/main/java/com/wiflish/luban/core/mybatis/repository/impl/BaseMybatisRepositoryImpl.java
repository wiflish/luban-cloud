package com.wiflish.luban.core.mybatis.repository.impl;

import cn.hutool.core.util.ReflectUtil;
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
import com.wiflish.luban.core.mybatis.query.QueryFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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
        PO po = getConverter().toPO(query);

        LambdaQueryWrapper<PO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Method[] methods = ReflectUtil.getMethods(po.getClass());
        for (Method method : methods) {
            if (!method.getName().startsWith("get")) {
                continue;
            }
            try {
                Object value = method.invoke(po);
                lambdaQueryWrapper.eq(value != null, t -> {
                    try {
                        return method.invoke(po);
                    } catch (Exception ignored) {
                    }
                    return null;
                }, value);
            } catch (Exception e) {
                log.warn("拼接SQL失败，方法名：{}", method.getName(), e);
            }
        }

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
        QueryFunction<Q, PO> queryFunction = getQueryFunction();
        LambdaQueryWrapper<PO> lambdaQueryWrapper = queryFunction.apply(query);
        if (lambdaQueryWrapper == null) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
        }

        Page<PO> pageFromDB = getMapper().selectPage(new Page<>(pager.getPage(), pager.getSize(), pager.getNeedTotal()), lambdaQueryWrapper);
        List<E> entities = pageFromDB.getRecords().stream().map(po -> getConverter().toEntity(po)).toList();

        return ListResponse.of(entities, pageFromDB.getTotal(), pager.getPage(), pager.getSize());
    }
}
