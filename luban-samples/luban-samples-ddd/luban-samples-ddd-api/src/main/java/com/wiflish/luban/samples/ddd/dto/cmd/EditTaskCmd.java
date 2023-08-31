package com.wiflish.luban.samples.ddd.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新增任务Cmd
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EditTaskCmd extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -3533082655423111828L;

    /**
     * 任务名称.
     */
    private String name;

    /**
     * 任务描述.
     */
    private String remark;
}
