package com.wiflish.luban.samples.ddd.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 修改任务状态.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EditTaskStatusCmd extends Command implements Serializable {
    private Long id;
    private Integer status;
}
