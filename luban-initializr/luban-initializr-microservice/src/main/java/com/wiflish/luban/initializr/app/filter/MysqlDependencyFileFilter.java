package com.wiflish.luban.initializr.app.filter;

import cn.hutool.core.collection.ListUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * mysql依赖处理.
 *
 * @author wiflish
 * @since 2022-10-26
 */
@Component
public class MysqlDependencyFileFilter extends AbstractDependencyFileFilter {
    private static final List<String> filterFileNames = ListUtil.of(
            "TaskPO", "TaskMapper", "TaskRepositoryImpl",
            "CodeGenerator", "CodeGeneratorTest",
            "MybatisPlusConfig", "MybatisPlusUtil");

    @Override
    protected List<String> getFilterFileNames() {
        return filterFileNames;
    }

    @Override
    String getDependencyId() {
        return "mysql";
    }
}
