package ${package.Parent}.domain.entity;

import com.wiflish.luban.core.domain.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * ${table.comment!} Entity
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
public class ${luban.entityName} extends Entity {
    @Serial
    private static final long serialVersionUID = 1L;

<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>
<#------------  END 字段循环遍历  ---------->
}