package com.wiflish.luban.generator;

import com.wiflish.luban.generator.dto.GeneratorDTO;

/**
 * @author wiflish
 * @since 2023-10-07
 */
public interface CodeGenerator {
    /**
     * 生成代码.
     *
     * @param generatorDTO
     */
    void generate(GeneratorDTO generatorDTO);
}
