/*-
 * #%L
 * %%
 * Copyright (C) 2023 wiflish
 * %%
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
 * #L%
 */
package com.wiflish.luban.core.dto.query;

import com.wiflish.luban.core.dto.BaseDTO;

import java.io.Serial;
import java.util.List;

/**
 * 查询参数.
 *
 * @author wiflish
 * @since 2022-03-17
 */
public abstract class Query extends BaseDTO {
    @Serial
    private static final long serialVersionUID = 3614146467808258286L;

    private String keyword;

    private List<OrderBy> orderBys;

    private String groupBy;

    public String getKeyword() {
        return keyword;
    }

    public Query setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

    public List<OrderBy> getOrderBys() {
        return orderBys;
    }

    public Query setOrderBys(List<OrderBy> orderBys) {
        this.orderBys = orderBys;
        return this;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public Query setGroupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public String toOrderBySql() {
        return this.toOrderBySql(true);
    }

    public String toOrderBySql(boolean needOrderByKey) {
        if (this.orderBys == null || this.orderBys.isEmpty()) {
            return "";
        }
        StringBuilder orderBySql = new StringBuilder();
        if (needOrderByKey) {
            orderBySql.append(" order by");
        }
        for (int i = 0, len = orderBys.size(); i < len; i++) {
            OrderBy orderBy = orderBys.get(i);
            if (i > 0) {
                orderBySql.append(", ");
            }
            orderBySql.append(" ").append(orderBy.getField()).append(" ");
            orderBySql.append(orderBy.getDirection());
        }

        return orderBySql.toString();
    }
}
