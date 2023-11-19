package com.wiflish.luban.generator.config;

import lombok.Data;

/**
 * @author wiflish
 * @since 2023-11-18
 */
@Data
public class LubanGeneratorConfig {
    /**
     * 领域模型中的实体名称, 首字母大写.
     */
    private String entityName;

    /**
     * 领域模型中的实体名称, 首字母小写.
     */
    private String entityNameCamel;

    /**
     * Controller的映射路径.
     */
    private String mapping;


}
