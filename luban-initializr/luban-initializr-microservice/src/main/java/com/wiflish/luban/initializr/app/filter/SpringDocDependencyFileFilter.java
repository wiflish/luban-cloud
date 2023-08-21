package com.wiflish.luban.initializr.app.filter;

import cn.hutool.core.collection.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 处理springdoc.
 * @author wiflish
 * @since 2022-10-27
 */
@Component
public class SpringDocDependencyFileFilter extends AbstractDependencyFileFilter {
    private static final List<String> filterFileNames = ListUtil.of(
            "OpenApiConfig");
    @Override
    protected List<String> getFilterFileNames() {
        return filterFileNames;
    }

    @Override
    String getDependencyId() {
        return "springdoc";
    }
}
