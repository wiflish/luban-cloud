package com.wiflish.luban.generator.domain.entity;

import cn.hutool.core.collection.ListUtil;
import com.wiflish.luban.core.domain.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 代码生成实体.
 *
 * @author wiflish
 * @since 2023-10-09
 */
@Getter
@Setter
public class CodeGeneratorEntity extends Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = 5429015360123068542L;

    private Long datasourceId;
    private List<String> includeTableNames = ListUtil.empty();
    private List<String> tablePrefixNames = ListUtil.empty();
    private Boolean needSkeleton = true;
    private String author = "luban";
    private String parentPackage;
    private String outputBasePath;

    public void generate() {

    }
}
