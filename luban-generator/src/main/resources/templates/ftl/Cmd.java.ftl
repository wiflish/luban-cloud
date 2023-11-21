package ${package.Parent}.api.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import lombok.Getter;
import lombok.Setter;
<#list luban.importPackages as pkg>
import ${pkg};
</#list>

import java.io.Serial;
import java.io.Serializable;

/**
 * ${table.comment!} 命令参数
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
public class ${luban.entityName}EditCmd extends Command implements Serializable {
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