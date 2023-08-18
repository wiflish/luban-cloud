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
/**
 * DDD::domain 业务领域层，聚焦于业务层面，负责表达业务逻辑，<br>
 * 包含：业务概念、业务模型、业务关系、业务状态、业务规则等内容。<br>
 * 在业务领域层需要用到的其他能力时，都通过定义仓储服务、领域服务等接口的形式，把具体的能力转交给基础设施层去实现。
 *
 * @author wiflish
 * @since 2023-08-18
 */
package com.wiflish.luban.samples.featuretype.domain;
