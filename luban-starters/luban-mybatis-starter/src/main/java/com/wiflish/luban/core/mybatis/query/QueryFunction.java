package com.wiflish.luban.core.mybatis.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wiflish.luban.core.infra.po.BasePO;

/**
 * QueryFunction，处理查询的条件封装。
 *
 * @author wiflish
 * @since 2023-11-12
 */
@FunctionalInterface
public interface QueryFunction<Q, PO extends BasePO> {
    /**
     * @param query 查询条件
     * @return 返回拼装好的查询对象
     */
    LambdaQueryWrapper<PO> apply(Q query);
}
