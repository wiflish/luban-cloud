package com.wiflish.luban.core.mybatis.typehandler;

import com.wiflish.luban.core.infra.po.FeatureBit;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * {@link FeatureBit}类的Mybatis类型转换处理类.
 *
 * @author wiflish
 * @since 2012-9-14
 * @see FeatureBit
 */
@MappedTypes({ FeatureBit.class })
@MappedJdbcTypes({ JdbcType.BIGINT })
public class FeatureBitTypeHandler extends BaseTypeHandler<FeatureBit> {
    private static final long defaultValue = 0L;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FeatureBit parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getValue());
    }

    @Override
    public FeatureBit getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return buildFeatureBit(rs.getLong(columnName));
    }

    @Override
    public FeatureBit getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return buildFeatureBit(rs.getLong(columnIndex));
    }

    @Override
    public FeatureBit getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return buildFeatureBit(cs.getLong(columnIndex));
    }

    private FeatureBit buildFeatureBit(long featureBit) {
        if (featureBit < defaultValue) {
            return null;
        }
        return new FeatureBit(featureBit);
    }
}
