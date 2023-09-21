/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
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
        Long userId = 0L;
        if (authentication != null) {
            CurrentUser principal = (CurrentUser) authentication.getPrincipal();
            if (principal != null) {
                userId = principal.getUserId();
            }
        }
        log.debug("start insert autofill....");
        this.strictInsertFill(metaObject, lubanMybatisAutofillProperties.getCreateIdField(), Long.class, userId);
        this.strictInsertFill(metaObject, lubanMybatisAutofillProperties.getUpdateIdField(), Long.class, userId);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = 0L;
        if (authentication != null) {
            CurrentUser principal = (CurrentUser) authentication.getPrincipal();
            if (principal != null) {
                userId = principal.getUserId();
            }
        }
        log.debug("start update autofill....");
        this.strictUpdateFill(metaObject, lubanMybatisAutofillProperties.getUpdateIdField(), Long.class, userId);
    }
}
