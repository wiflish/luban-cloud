package ${package.Parent}.infra.converter;

import cn.hutool.core.bean.BeanUtil;
import ${package.Parent}.domain.entity.${luban.entityName};
import ${package.Parent}.api.dto.query.${luban.entityName}Query;
import ${package.Parent}.infra.po.${luban.entityName}PO;
import com.wiflish.luban.core.infra.converter.BaseConverter;
import org.springframework.stereotype.Component;

/**
 * ${table.comment!} Converter
 *
 * @author ${author}
 * @since ${date}
 */
@Component
public class ${luban.entityName}Converter implements BaseConverter<${luban.entityName}Query, ${luban.entityName}, ${luban.entityName}PO> {
    @Override
    public ${luban.entityName}PO toPO(${luban.entityName} entity) {
        ${luban.entityName}PO ${luban.entityNameCamel}PO = new ${luban.entityName}PO();
        BeanUtil.copyProperties(entity, ${luban.entityNameCamel}PO);

        return ${luban.entityNameCamel}PO;
    }

    @Override
    public ${luban.entityName}PO toPO(${luban.entityName}Query query) {
        ${luban.entityName}PO ${luban.entityNameCamel}PO = new ${luban.entityName}PO();
        BeanUtil.copyProperties(query, ${luban.entityNameCamel}PO);

        return ${luban.entityNameCamel}PO;
    }

    @Override
    public ${luban.entityName} toEntity(${luban.entityName}PO po) {
        ${luban.entityName} ${luban.entityNameCamel} = new ${luban.entityName}();
        BeanUtil.copyProperties(po, ${luban.entityNameCamel});

        return ${luban.entityNameCamel};
    }
}