package com.wiflish.luban.core.mybatis.objecthandler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wiflish.luban.core.dto.CurrentUser;
import com.wiflish.luban.core.mybatis.config.LubanMybatisAutofillProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * autofill createId and updateId.
 *
 * @author wiflish
 * @since 2023-08-22
 */
@Slf4j
@AllArgsConstructor
public class AuditMetaObjectHandler implements MetaObjectHandler {
    private LubanMybatisAutofillProperties lubanMybatisAutofillProperties;

    @Override
    public void insertFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CurrentUser principal = (CurrentUser) authentication.getPrincipal();
        if (principal == null) {
            return;
        }

        log.debug("start insert autofill....");
        Long userId = principal.getUserId();
        this.strictInsertFill(metaObject, lubanMybatisAutofillProperties.getCreateIdField(), Long.class, userId);
        this.strictInsertFill(metaObject, lubanMybatisAutofillProperties.getUpdateIdField(), Long.class, userId);
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
        this.strictUpdateFill(metaObject, lubanMybatisAutofillProperties.getUpdateIdField(), Long.class, userId);
    }
}
