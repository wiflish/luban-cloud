package com.wiflish.luban.generator.api.service;

import com.wiflish.luban.generator.api.dto.GeneratorDTO;

/**
 * @author wiflish
 * @since 2023-10-07
 */
public interface CodeGenerateAppService {
    /**
     * 生成代码.
     *
     * @param generatorDTO
     */
    void generate(GeneratorDTO generatorDTO);
}
