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
package com.wiflish.luban.core.mybatis.typehandler;

import cn.hutool.json.JSONUtil;
import com.wiflish.luban.core.infra.po.FeatureJson;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@MappedTypes({ FeatureJson.class })
@MappedJdbcTypes({ JdbcType.VARCHAR })
@Slf4j
public class FeatureJsonTypeHandler extends BaseTypeHandler<FeatureJson> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FeatureJson parameter, JdbcType jdbcType) throws SQLException {
        String value = parameter.getValue();
        if (!JSONUtil.isTypeJSON(value)) {
            log.error("参数不是json结构，保存失败. 参数值：{}", value);
            return;
        }
        ps.setString(i, value);
    }

    @Override
    public FeatureJson getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return buildFeatureJson(rs.getString(columnName));
    }

    @Override
    public FeatureJson getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return buildFeatureJson(rs.getString(columnIndex));
    }

    @Override
    public FeatureJson getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return buildFeatureJson(cs.getString(columnIndex));
    }

    private FeatureJson buildFeatureJson(String json) {
        return new FeatureJson(json);
    }
}
