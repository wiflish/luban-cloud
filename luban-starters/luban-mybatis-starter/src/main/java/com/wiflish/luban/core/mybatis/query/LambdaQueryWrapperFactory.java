package com.wiflish.luban.core.mybatis.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.infra.po.BasePO;

/**
 * @author wiflish
 * @since 2023-08-30
 */
public interface LambdaQueryWrapperFactory<PO extends BasePO, Q extends Query> {
    String getWrapperId();

    LambdaQueryWrapper<PO> getLambdaQueryWrapper(Q query);
}
