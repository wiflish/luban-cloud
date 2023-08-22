package com.wiflish.luban.core.mybatis.objecthandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wiflish.luban.core.dto.CurrentUser;
import com.wiflish.luban.core.mybatis.config.LubanMybatisProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * autofill createId and updateId.
 *
 * @author wiflish
 * @since 2023-08-22
 */
@Slf4j
@Component
public class AuditMetaObjectHandler implements MetaObjectHandler {
    @Autowired
    private LubanMybatisProperties lubanMybatisProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser principal = (CurrentUser) authentication.getPrincipal();
        if (principal == null) {
            return;
        }

        log.debug("start insert autofill....");
        Long userId = principal.getUserId();
        this.strictInsertFill(metaObject, lubanMybatisProperties.getCreateIdField(), Long.class, userId);
        this.strictInsertFill(metaObject, lubanMybatisProperties.getUpdateIdField(), Long.class, userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser principal = (CurrentUser) authentication.getPrincipal();
        if (principal == null) {
            return;
        }

        log.debug("start update autofill....");
        Long userId = principal.getUserId();
        this.strictUpdateFill(metaObject, lubanMybatisProperties.getUpdateIdField(), Long.class, userId);
    }
}
