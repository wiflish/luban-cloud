package ${package.Parent}.domain.repository;

import ${package.Parent}.domain.entity.${luban.entityName};
import ${package.Parent}.api.dto.query.${luban.entityName}Query;
import com.wiflish.luban.core.domain.repository.BaseRepository;

/**
 * ${table.comment!} Repository
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${luban.entityName}Repository extends BaseRepository<${luban.entityName}Query, ${luban.entityName}> {
}