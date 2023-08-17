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

/**
 * 扩展字段类型处理. 使用方法详见User实体的单元测试。
 * 
 * @author wiflish
 */
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
