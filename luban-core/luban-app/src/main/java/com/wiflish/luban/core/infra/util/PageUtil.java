package com.wiflish.luban.core.infra.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.constant.BaseConstant;

import static com.wiflish.luban.core.dto.constant.BaseConstant.DEFAULT_PAGE_NO;

/**
 *
 * @author wiflish
 * @since 2023-11-08
 */
public class PageUtil {
    public static <T> Page<T> toMybatisPlusPage(Pager pager) {
        int pageNo = pager.getPage() == null ? DEFAULT_PAGE_NO : pager.getPage();
        int pageSize = pager.getSize() == null ? BaseConstant.DEFAULT_PAGE_SIZE : pager.getSize();
        boolean needTotal = pager.getNeedTotal() != null && pager.getNeedTotal();

        return new Page<>(pageNo, pageSize, needTotal);
    }
}
