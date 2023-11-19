package ${package.Parent}.app.service.impl;

import ${package.Parent}.domain.repository.${luban.entityName}Repository;
import ${package.Parent}.api.service.${luban.entityName}AService;
import ${package.Parent}.app.assembler.${luban.entityName}Assembler;
import com.wiflish.luban.core.app.service.BaseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} ServiceImpl
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@RequiredArgsConstructor
public class ${luban.entityName}AServiceImpl extends BaseServiceImpl implements ${luban.entityName}AService {
    private final ${luban.entityName}Assembler ${luban.entityNameCamel}Assembler;
    private final ${luban.entityName}Repository ${luban.entityNameCamel}Repository;

    @Override
    public ${luban.entityName}Assembler getAssembler() {
        return ${luban.entityNameCamel}Assembler;
    }

    @Override
    public ${luban.entityName}Repository getRepository() {
        return ${luban.entityNameCamel}Repository;
    }
}
