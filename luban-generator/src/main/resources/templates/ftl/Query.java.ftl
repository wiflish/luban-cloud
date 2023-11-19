package ${package.Parent}.api.dto.query;

import com.wiflish.luban.core.dto.query.Query;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
<#list luban.importPackages as pkg>
import ${pkg};
</#list>

import java.io.Serial;
import java.io.Serializable;

/**
 * ${table.comment!} Query
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
@Schema(description = "${table.comment!}-查询")
public class ${luban.entityName}Query extends Query implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.comment!?length gt 0>
    @Schema(description = "${field.comment}")
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}