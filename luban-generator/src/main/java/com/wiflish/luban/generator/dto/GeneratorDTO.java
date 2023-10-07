package com.wiflish.luban.generator.dto;

import cn.hutool.core.collection.ListUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author wiflish
 * @since 2023-10-07
 */
@Data
public class GeneratorDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5429015360123068542L;

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private List<String> includeTableNames = ListUtil.empty();
    private List<String> tablePrefixNames = ListUtil.empty();
    private String author;
    private String parentPackage;
    private String outputBasePath;
}
