package ${package.Parent}.api.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * ${table.comment!} Cmd
 *
 * @author ${author}
 * @since ${date}
 */
@Getter
@Setter
@Schema(description = "${table.comment!}-操作")
public class ${luban.entityName}EditCmd extends Command implements Serializable {
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