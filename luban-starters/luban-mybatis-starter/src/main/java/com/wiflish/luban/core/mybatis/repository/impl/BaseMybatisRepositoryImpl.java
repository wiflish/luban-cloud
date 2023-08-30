package com.wiflish.luban.core.mybatis.repository.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wiflish.luban.core.domain.entity.BaseEntity;
import com.wiflish.luban.core.domain.repository.BaseRepository;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import com.wiflish.luban.core.infra.po.BasePO;
import com.wiflish.luban.core.mybatis.query.MybatisQuery;
import com.wiflish.luban.core.mybatis.query.QueryMapping;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Slf4j
public abstract class BaseMybatisRepositoryImpl<E extends BaseEntity, PO extends BasePO, Q extends Query> implements BaseRepository<E, Q> {
    /**
     * 返回PO对象。
     */
    protected abstract PO newPO();

    protected abstract BaseMapper<PO> getMapper();

    protected abstract BaseConverter<E, PO> getConverter();

    protected abstract MybatisQuery getMybatisQuery();

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
        Method[] methods = ReflectUtil.getMethods(newPO().getClass());

        MybatisQuery mybatisQuery = getMybatisQuery();


        Map<String, QueryMapping> mappingMap = mybatisQuery.getMappingMap(query);
        if (MapUtil.isEmpty(mappingMap)) {
            QueryWrapper<PO> wrapper = new QueryWrapper<>();
            wrapper.last("limit 2000");
            List<PO> pos = getMapper().selectList(wrapper);

            return pos.stream().map(po -> getConverter().toEntity(po)).toList();
        }

        LambdaQueryWrapper<PO> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        mappingMap.keySet().forEach(key -> {
            QueryMapping queryMapping = mappingMap.get(key);

//            lambdaQueryWrapper.
        });

        return null;
    }

    @Override
    public Response<List<E>> listPage(Q query, Pager pager) {
        return null;
    }
}
