package ${package.Parent}.app.assembler;

import cn.hutool.core.bean.BeanUtil;
import ${package.Parent}.domain.entity.${luban.entityName};
import ${package.Parent}.api.dto.${luban.entityName}DTO;
import ${package.Parent}.api.dto.cmd.${luban.entityName}EditCmd;
import com.wiflish.luban.core.assembler.Assembler;
import org.springframework.stereotype.Component;

/**
 * ${table.comment!} Assembler
 *
 * @author ${author}
 * @since ${date}
 */
@Component
public class ${luban.entityName}Assembler implements Assembler<${luban.entityName}EditCmd, ${luban.entityName}DTO, ${luban.entityName}> {
    @Override
    public ${luban.entityName} toEntity(${luban.entityName}DTO dto) {
        ${luban.entityName} ${luban.entityNameCamel} = new ${luban.entityName}();
        BeanUtil.copyProperties(dto, ${luban.entityNameCamel});

        return ${luban.entityNameCamel};
    }

    @Override
    public ${luban.entityName} toEntity(${luban.entityName}EditCmd cmd) {
        ${luban.entityName} ${luban.entityNameCamel} = new ${luban.entityName}();
        BeanUtil.copyProperties(cmd, ${luban.entityNameCamel});

        return ${luban.entityNameCamel};
    }

    @Override
    public ${luban.entityName}DTO toDTO(${luban.entityName} entity) {
        ${luban.entityName}DTO ${luban.entityNameCamel}DTO = new ${luban.entityName}DTO();
        BeanUtil.copyProperties(entity, ${luban.entityNameCamel}DTO);

        return ${luban.entityNameCamel}DTO;
    }
}