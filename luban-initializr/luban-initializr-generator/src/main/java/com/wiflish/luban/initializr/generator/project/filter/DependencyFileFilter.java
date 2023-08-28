/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
package com.wiflish.luban.initializr.generator.project.filter;

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
