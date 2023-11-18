package com.wiflish.luban.generator.config;

import lombok.Data;

/**
 * @author wiflish
 * @since 2023-11-18
 */
@Data
public class LubanGeneratorConfig {
    /**
     * 领域模型中的实体名称.
     */
    private String entityName;

    /**
     * Controller的映射路径.
     */
    private String mapping;


}
