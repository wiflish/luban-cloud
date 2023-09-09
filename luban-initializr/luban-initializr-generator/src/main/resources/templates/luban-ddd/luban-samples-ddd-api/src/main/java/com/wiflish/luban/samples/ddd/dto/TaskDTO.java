package com.wiflish.luban.samples.ddd.dto;

import com.wiflish.luban.core.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TaskDTO extends DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 任务名称.
     */
    private String name;

    /**
     * 任务描述.
     */
    private String remark;

    /**
     * 任务状态. 1=待开始; 2=进行中; 3=已完成
     */
    private Integer status;
}
