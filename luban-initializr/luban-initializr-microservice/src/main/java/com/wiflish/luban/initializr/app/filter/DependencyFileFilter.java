package com.wiflish.luban.initializr.app.filter;

import io.spring.initializr.generator.buildsystem.Dependency;

import java.nio.file.Path;
import java.util.Map;

/**
 * 文件过滤器. 当依赖没有引入时，其相关的文件要过滤掉。<br>
 * 比如：没有引入mysql依赖，则数据库、mybatis等相关的文件都需要过滤掉。
 *
 * @author wiflish
 * @since 2022-10-26
 */
public interface DependencyFileFilter {
    /**
     * 当依赖匹配未匹配时，判断该文件路径中的文件是否需要过滤掉。
     *
     * @param filePath
     * @param requestedDependencies
     *
     * @return true=表示排除掉，不生成.
     */
    boolean filtered(Path filePath, Map<String, Dependency> requestedDependencies);
}
