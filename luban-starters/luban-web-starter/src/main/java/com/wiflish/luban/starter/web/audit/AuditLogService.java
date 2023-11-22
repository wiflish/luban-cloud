package com.wiflish.luban.starter.web.audit;

/**
 * @author wiflish
 * @since 2023-11-22
 */
public interface AuditLogService {
    void save(AuditLogDTO auditLogDTO);
}
