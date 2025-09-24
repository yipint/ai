package com.typ.business.handler;

import com.winning.imism.common.enums.LogStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: TODO
 * @author: typ
 * @date: 2025/9/12
 * @version: 1.0
 */
public class LogStatusEnumTypeHandler extends BaseTypeHandler<LogStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LogStatusEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());  // 使用枚举的 code 值
    }

    @Override
    public LogStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : LogStatusEnum.getEnum(code);
    }

    @Override
    public LogStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        // 处理按列索引获取值的情况
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : LogStatusEnum.getEnum(code);
    }

    @Override
    public LogStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        // 处理存储过程返回值的情况
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : LogStatusEnum.getEnum(code);
    }

    // 其他方法（getNullableResult 重载）同理实现
}
