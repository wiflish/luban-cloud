package com.wiflish.luban.samples.ddd.dto.cmd;

import com.wiflish.luban.core.dto.Command;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 修改任务状态.
 *
 * @author wiflish
 * @since 2023-08-28
 */
@Getter
@Setter
public class EditTaskStatusCmd extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -5238068493293229464L;

    @Min(1)
    private Long id;

    @Min(1)
    @Max(10)
    private Integer status;
}
