package com.wiflish.luban.samples.mybatis.infra.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wiflish.luban.core.infra.po.BasePO;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiezhengrong
 * @since 2023-08-20
 */
@Getter
@Setter
@TableName("T_USER")
public class UserPO extends BasePO {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;
}
