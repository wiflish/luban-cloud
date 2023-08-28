package com.wiflish.luban.samples.ddd.infra.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wiflish.luban.core.infra.po.BasePO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 任务.
 * </p>
 *
 * @author wiflish
 * @since 2023-08-28 20:07:22
 */
@Getter
@Setter
@TableName("t_task")
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
