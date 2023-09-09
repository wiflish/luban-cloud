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
package com.wiflish.luban.core.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entity的抽象基类.
 *
 * @author wiflish
 * @since 2022-08-26
 */
@Getter
@Setter
public abstract class Entity implements Serializable {
    @Serial
    private static final long serialVersionUID = -1794219784054488134L;

    protected Long id;

    /**
     * 创建人id.
     */
    private Long createId;

    /**
     * 修改人id.
     */
    private Long updateId;

    /**
     * 创建时间.
     */
    private LocalDateTime createTime;

    /**
     * 更新时间.
     */
    private LocalDateTime updateTime;

    /**
     * 乐观锁字段.
     */
    private Long version;

    /**
     * 逻辑删除标记.
     */
    private Long deleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entity entity)) {
            return false;
        }
        if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        if (this.id == null || entity.getId() == null) {
            return false;
        }

        return this.id.equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return this.getId().hashCode();
    }
}
