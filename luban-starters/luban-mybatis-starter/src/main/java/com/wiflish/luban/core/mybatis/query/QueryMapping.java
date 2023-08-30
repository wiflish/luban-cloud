package com.wiflish.luban.core.mybatis.query;

import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-08-30
 */
@Getter
@Setter
public class QueryMapping {
    /**
     * 查询属性
     */
    private String propertyName;

    /**
     * 对应字段.
     */
    private SFunction sFunction;

    /**
     * 查询操作符.
     */
    private SqlKeyword sqlKeyword;
}
