package com.waffle.infra.queue.typehandler;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.UUID;

@SuppressWarnings("unused")
@MappedTypes({UUID.class})
public class UuidTypeHandler implements TypeHandler<UUID> {
    private static final Logger LOG = LoggerFactory.getLogger(UuidTypeHandler.class);

    @Override
    public void setParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        LOG.info("Setting UUID as : {}", parameter.toString());
        ps.setObject(i, parameter.toString());
    }

    @Override
    public UUID getResult(ResultSet rs, String columnName) throws SQLException {
        return toUUID(rs.getString(columnName));
    }

    @Override
    public UUID getResult(ResultSet rs, int columnIndex) throws SQLException {
        return toUUID(rs.getString(columnIndex));
    }

    @Override
    public UUID getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toUUID(cs.getString(columnIndex));
    }

    private static UUID toUUID(String val) {
        if (val != null && val.length()==0) {
            try {
                return UUID.fromString(val);
            } catch (IllegalArgumentException e) {
                LOG.warn("Bad UUID found: {}", val);
            }
        }
        return null;
    }

}