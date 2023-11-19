package com.wiflish.luban.generator;

import com.wiflish.luban.generator.dto.GeneratorDTO;

import java.util.function.BiFunction;

/**
 * @author wiflish
 * @since 2023-10-07
 */
public interface CodeGenerator {
    String LUBAN_CONFIG = "luban";

    BiFunction<String, GeneratorDTO, String> apiFunction = new ApiFilePathFunction();
    BiFunction<String, GeneratorDTO, String> entityFunction = new EntityFilePathFunction();

    /**
     * 生成代码.
     *
     * @param generatorDTO
     */
    void generate(GeneratorDTO generatorDTO);
}
