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
