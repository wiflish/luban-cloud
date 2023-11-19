package ${package.Parent}.infra.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ${package.Parent}.domain.entity.${luban.entityName};
import ${package.Parent}.domain.repository.${luban.entityName}Repository;
import ${package.Parent}.api.dto.query.${luban.entityName}Query;
import ${package.Parent}.infra.converter.${luban.entityName}Converter;
import ${package.Parent}.infra.mapper.${luban.entityName}Mapper;
import ${package.Parent}.infra.po.${luban.entityName}PO;
import ${package.Parent}.infra.query.${luban.entityName}QueryWrapper;
import com.wiflish.luban.starter.mybatis.query.QueryFunction;
import com.wiflish.luban.starter.mybatis.repository.impl.BaseMybatisRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * ${table.comment!} Repository
 *
 * @author ${author}
 * @since ${date}
 */
@Repository
@RequiredArgsConstructor
public class ${luban.entityName}RepositoryImpl extends BaseMybatisRepositoryImpl<${luban.entityName}Query, ${luban.entityName}, ${luban.entityName}PO> implements ${luban.entityName}Repository {
    private final ${luban.entityName}Mapper ${luban.entityNameCamel}Mapper;
    private final ${luban.entityName}Converter ${luban.entityNameCamel}Converter;
    private final ${luban.entityName}QueryWrapper ${luban.entityNameCamel}QueryWrapper;

    @Override
    protected ${luban.entityName}Mapper getMapper() {
        return ${luban.entityNameCamel}Mapper;
    }

    @Override
    public ${luban.entityName}Converter getConverter() {
        return ${luban.entityNameCamel}Converter;
    }

    @Override
    public QueryFunction<${luban.entityName}Query, ${luban.entityName}PO> getQueryFunction() {
        return ${luban.entityNameCamel}QueryWrapper;
    }
}