package com.wiflish.luban.core.mybatis.query;

import com.wiflish.luban.core.dto.query.Query;

import java.util.Map;

/**
 * @author wiflish
 * @since 2023-08-30
 */
public interface MybatisQuery {
    Map<String, QueryMapping> getMappingMap(Query query);
}
