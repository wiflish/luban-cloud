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

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 数据库排序
 *
 * @author wiflish
 * @since 2022-10-13
 */
public class OrderBy implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    public static final List<String> directionList = Arrays.asList(ASC, DESC);

    /**
     * 排序的字段
     */
    private String field;

    private String direction;

    /**
     * 构造
     *
     * @param field 排序字段
     */
    public OrderBy(String field) {
        this.field = field;
        this.direction = ASC;
    }

    public OrderBy(String field, String direction) {
        this(field);
        buildDirection(direction);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        buildDirection(direction);
    }

    private void buildDirection(String direction) {
        if (directionList.contains(direction)) {
            this.direction = direction;
        } else {
            this.direction = ASC;
        }
    }

    public boolean isAsc() {
        return ASC.equalsIgnoreCase(this.direction);
    }

    @Override
    public String toString() {
        return "Order{" +
                "field='" + field + '\'' +
                ", direction=" + direction +
                '}';
    }
}
