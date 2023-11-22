package com.wiflish.luban.samples.ddd.dto.query;

import com.wiflish.luban.core.dto.query.Query;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 任务查询.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Getter
@Setter
public class TaskQuery extends Query {
    @Serial
    private static final long serialVersionUID = -163286587368888564L;

    private String taskName;
    private Integer status;
}
