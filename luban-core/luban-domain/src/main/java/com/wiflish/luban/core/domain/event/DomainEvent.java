/*-
 * ************
 * luban-cloud
 * ************
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ************
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
package com.wiflish.luban.core.domain.event;

import org.springframework.context.ApplicationEvent;

import java.io.Serial;
import java.io.Serializable;

/**
 * 领域事件.
 *
 * @author wiflish
 * @since 2022-08-29
 */
public abstract class DomainEvent extends ApplicationEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 2598204981451372620L;

    /**
     * Create a new {@code DomainEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public DomainEvent(Object source) {
        super(source);
    }
}
