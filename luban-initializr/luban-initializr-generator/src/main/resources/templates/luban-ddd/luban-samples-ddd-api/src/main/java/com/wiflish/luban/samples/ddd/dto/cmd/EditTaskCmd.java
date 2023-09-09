package com.wiflish.luban.samples.ddd.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 新增任务Cmd
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Getter
@Setter
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
