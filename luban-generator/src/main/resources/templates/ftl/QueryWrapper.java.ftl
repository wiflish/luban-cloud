package ${package.Parent}.infra.query;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ${package.Parent}.api.dto.query.${luban.entityName}Query;
import ${package.Parent}.infra.po.${luban.entityName}PO;
import com.wiflish.luban.starter.mybatis.query.QueryFunction;
import org.springframework.stereotype.Component;

/**
 * ${table.comment!} QueryWrapper
 *
 * @author ${author}
 * @since ${date}
 */
@Component
public class ${luban.entityName}QueryWrapper implements QueryFunction<${luban.entityName}Query, ${luban.entityName}PO> {
    @Override
    public LambdaQueryWrapper<${luban.entityName}PO> apply(${luban.entityName}Query query) {
        LambdaQueryWrapper<${luban.entityName}PO> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        return lambdaQueryWrapper;
    }
}