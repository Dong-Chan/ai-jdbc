package com.github.dongchan.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dongchan Year
 */
public class Mappers {

    public static final SingleResultMapper<Integer> SINGLE_INT = new SingleResultMapper<>(rs -> rs.getInt(1));

    public static class SingleResultMapper<T> implements ResultSetMapper<T>{

        private final RowMapper<T> rowMapper;

        public SingleResultMapper(RowMapper<T> rowMapper) {
            this.rowMapper = rowMapper;
        }

        @Override
        public T map(ResultSet rs) throws SQLException {
            boolean first = rs.next();
            if (!first){
                throw new SingleResultExpected("Expected single result in ResultSet, but had none.");
            }
            final T result = rowMapper.map(rs);
            boolean second = rs.next();
            if (second){
                throw new SingleResultExpected("Expected single result in ResultSet, but had more than 1.");
            }

            return result;
        }
    }

    public static class SingleResultExpected extends SQLRuntimeException{
        public SingleResultExpected(String message) {
            super(message);
        }
    }
}
