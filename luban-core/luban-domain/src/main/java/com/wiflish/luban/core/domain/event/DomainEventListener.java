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

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

/**
 * 领域事件监听器.
 *
 * @author wiflish
 * @since 2022-08-30
 */
public interface DomainEventListener<E extends DomainEvent> extends ApplicationListener<E> {
    /**
     * 处理事件.
     *
     * @param event ev
     */
    void onDomainEvent(E event);

    @Override
    default void onApplicationEvent(@NonNull E event) {
        onDomainEvent(event);
    }
}
