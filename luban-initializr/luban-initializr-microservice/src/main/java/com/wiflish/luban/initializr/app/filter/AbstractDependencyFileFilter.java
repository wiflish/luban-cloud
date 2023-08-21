package com.wiflish.luban.initializr.app.filter;

import io.spring.initializr.generator.buildsystem.Dependency;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * @author wiflish
 * @since 2022-10-27
 */
public abstract class AbstractDependencyFileFilter implements DependencyFileFilter {

    /**
     * 当没有配置依赖时，需要过滤的文件.
     *
     * @return
     */
    abstract protected List<String> getFilterFileNames();

    /**
     * 依赖的ID.
     *
     * @return
     */
    abstract String getDependencyId();

    @Override
    public boolean filtered(Path filePath, Map<String, Dependency> requestedDependencies) {
        //依赖中配置了mysql, 文件不需要过滤.
        if (requestedDependencies != null && requestedDependencies.containsKey(getDependencyId())) {
            return false;
        }
        String fileStr = filePath.toString();
        fileStr = fileStr.substring(0, fileStr.indexOf("."));
        for (String name : getFilterFileNames()) {
            //需要全量匹配.
            if (name.equalsIgnoreCase(fileStr)) {
                return true;
            }
        }
        return false;
    }
}
