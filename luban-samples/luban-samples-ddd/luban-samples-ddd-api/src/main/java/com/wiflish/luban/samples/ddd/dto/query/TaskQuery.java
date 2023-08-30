package com.wiflish.luban.samples.ddd.dto.query;

import com.wiflish.luban.core.dto.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 任务查询.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskQuery extends PageQuery {
    @Serial
    private static final long serialVersionUID = -163286587368888564L;

    private String taskName;
    private Integer status;
}
