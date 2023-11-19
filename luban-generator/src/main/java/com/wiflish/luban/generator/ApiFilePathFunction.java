package com.wiflish.luban.generator;

import cn.hutool.core.util.StrUtil;
import com.wiflish.luban.generator.dto.GeneratorDTO;

import java.util.function.BiFunction;

/**
 * @author wiflish
 * @since 2023-11-19
 */
public class ApiFilePathFunction implements BiFunction<String, GeneratorDTO, String> {
    @Override
    public String apply(String apiRootPath, GeneratorDTO generatorDTO) {
        StringBuilder resourcePath = new StringBuilder();
        resourcePath.append(apiRootPath);
        resourcePath.append("/src/main/java");
        resourcePath.append("/").append(generatorDTO.getBaseParentPackage().replace(".", "/"));
        if (StrUtil.isNotEmpty(generatorDTO.getBoundedContext())) {
            resourcePath.append("/").append(generatorDTO.getBoundedContext());
        }
        return resourcePath.toString();
    }
}
