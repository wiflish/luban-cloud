package com.wiflish.luban.core.infra.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.constant.BaseConstant;

/**
 *
 * @author wiflish
 * @since 2023-11-08
 */
public class PageUtil {
    public static <T> IPage<T> toIPage(Pager pager) {
        if (pager == null) {
            return new Page<>(BaseConstant.DEFAULT_PAGE_NO, BaseConstant.DEFAULT_PAGE_SIZE);
        }
        return new Page<>(pager.getPage(), pager.getSize());
    }
}
