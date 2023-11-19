package ${package.Parent}.api.service;

import com.wiflish.luban.core.domain.entity.Entity;
import com.wiflish.luban.core.dto.Command;
import com.wiflish.luban.core.dto.DTO;
import com.wiflish.luban.core.dto.query.Query;
import com.wiflish.luban.core.service.BaseService;

/**
 * ${table.comment!} Service
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${luban.entityName}AService<C extends Command, Q extends Query, T extends DTO, E extends Entity> extends BaseService<C, Q, T, E> {
}
