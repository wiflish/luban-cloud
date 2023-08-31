package com.wiflish.luban.core.mybatis.repository.impl;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.core.mybatis.query.LambdaQueryWrapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Slf4j
@Component
public abstract class BaseMybatisRepositoryImpl<E extends BaseEntity, PO extends BasePO, Q extends Query> implements BaseRepository<E, Q> {
    @Autowired
    protected List<LambdaQueryWrapperFactory<PO, Q>> factories;

    protected abstract BaseMapper<PO> getMapper();

    protected abstract BaseConverter<E, PO> getConverter();

    protected LambdaQueryWrapperFactory<PO, Q> getLambdaQueryWrapperFactory(Q query) {
        return (factories.stream().filter(factory -> factory.getWrapperId().equals(query.getWrapperId())).findFirst().orElse(null));
    }

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
        PO po = getConverter().toPO(entity);

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
        LambdaQueryWrapperFactory<PO, Q> lambdaQueryWrapperFactory = getLambdaQueryWrapperFactory(query);
        LambdaQueryWrapper<PO> lambdaQueryWrapper;
        if (lambdaQueryWrapperFactory == null) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
        } else {
            lambdaQueryWrapper = lambdaQueryWrapperFactory.getLambdaQueryWrapper(query);
        }
        lambdaQueryWrapper.last(" limit 2000 ");
        List<PO> pos = getMapper().selectList(lambdaQueryWrapper);

        return pos.stream().map(po -> getConverter().toEntity(po)).toList();
    }

    @Override
    public ListResponse<E> listPage(Q query, Pager pager) {
        LambdaQueryWrapperFactory<PO, Q> lambdaQueryWrapperFactory = getLambdaQueryWrapperFactory(query);
        LambdaQueryWrapper<PO> lambdaQueryWrapper;
        if (lambdaQueryWrapperFactory == null) {
            lambdaQueryWrapper = new LambdaQueryWrapper<>();
        } else {
            lambdaQueryWrapper = lambdaQueryWrapperFactory.getLambdaQueryWrapper(query);
        }

        Page<PO> pageFromDB = getMapper().selectPage(new Page<>(pager.getPage(), pager.getSize()), lambdaQueryWrapper);
        List<E> entities = pageFromDB.getRecords().stream().map(po -> getConverter().toEntity(po)).toList();

        return ListResponse.of(entities, pageFromDB.getTotal(), pager.getPage(), pager.getSize());
    }
}
