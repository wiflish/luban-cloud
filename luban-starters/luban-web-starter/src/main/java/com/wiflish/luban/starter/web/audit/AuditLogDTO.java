package com.wiflish.luban.starter.web.audit;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 审计日志DTO
 *
 * @author wiflish
 * @since 2023-11-22
 */
@Data
public class AuditLogDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作人id
     */
    private Long operateId;

    /**
     * 操作人姓名
     */
    private String realName;

    /**
     * 模块名
     */
    private String module;

    /**
     * 功能名
     */
    private String funcName;

    /**
     * 请求路径.
     */
    private String path;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 操作人的IP
     */
    private String ip;

    /**
     * 登录地点
     */
    private String address;

    /**
     * UserAgent
     */
    private String userAgent;

    /**
     * 审计类型
     */
    private Integer auditType;

    /**
     * 执行时长
     */
    private Integer duration;

    /**
     * 操作结果
     */
    private Integer result;
}
