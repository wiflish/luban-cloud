package com.wiflish.luban.samples.infra.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wiflish.luban.core.infra.po.BasePO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author wiflish
 * @since 2023-08-31
 */
@Getter
@Setter
@TableName("T_TASK")
public class TaskPO extends BasePO {

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
     * 任务状态. 1=待开始 2=进行中 3=已完成
     */
    private Integer status;
}
