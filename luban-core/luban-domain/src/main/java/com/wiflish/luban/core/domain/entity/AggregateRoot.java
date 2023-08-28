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

import com.wiflish.luban.core.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

/**
 * 聚合根基类
 *
 * @author wiflish
 * @since 2022-08-29
 */
@Getter
@Setter
public abstract class AggregateRoot extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1696567208629890967L;
    
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    public void clearEvents() {
        domainEvents.clear();
    }
}
