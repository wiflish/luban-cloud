package com.wiflish.luban.starter.web.audit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审计类型枚举
 *
 * @author wiflish
 * @since 2023-11-22
 */
@Getter
@AllArgsConstructor
public enum AuditTypeEnum {
    /**
     * 新增
     */
    INSERT("POST", 1),

    /**
     * 删除
     */
    DELETE("DELETE", 2),

    /**
     * 修改
     */
    UPDATE("PUT", 3),

    /**
     * 查询
     */
    GET("GET", 4),

    /**
     * 其它
     */
    OTHER("UNKNOWN", 99);

    private final String method;
    private final int value;

    public static AuditTypeEnum get(String method) {
        for (AuditTypeEnum auditTypeEnum : AuditTypeEnum.values()) {
            if (auditTypeEnum.getMethod().equals(method)) {
                return auditTypeEnum;
            }
        }
        return OTHER;
    }
}
