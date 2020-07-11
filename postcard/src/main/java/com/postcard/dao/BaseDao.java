package com.postcard.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

public abstract class BaseDao {

	@Autowired
	JdbcTemplate mainJdbcTemplate;

	public <T> List<T> query(String sql, String param, RowMapper<T> rowMapper) throws DataAccessException {
		return mainJdbcTemplate.query(sql, getPreparedStatementSetter(param), rowMapper);
	}

	public <T> T queryForObject(String sql, Class<T> requiredType, @Nullable Object... args) throws DataAccessException {
		return mainJdbcTemplate.queryForObject(sql, args, BeanPropertyRowMapper.newInstance(requiredType));
	}
	
    public <T> int update(String sql,  @Nullable Object... args) throws DataAccessException {
        return mainJdbcTemplate.update(sql, args);
    }
	
	public <T> List<T> query(String sql, String param, Class<T> clazz) throws DataAccessException {
		return mainJdbcTemplate.query(sql, getPreparedStatementSetter(param), BeanPropertyRowMapper.newInstance(clazz));
	}

	public <T> List<T> query(String sql, Long param, RowMapper<T> rowMapper) throws DataAccessException {
		return mainJdbcTemplate.query(sql, getPreparedStatementSetter(param), rowMapper);
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		return mainJdbcTemplate.query(sql, rowMapper);
	}

	private PreparedStatementSetter getPreparedStatementSetter(String param) {
		return new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, param);
			}
		};
	}

	public JdbcTemplate getMainJdbcTemplate() {
		return mainJdbcTemplate;
	}

	private PreparedStatementSetter getPreparedStatementSetter(Long param) {
		return new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, param);
			}
		};
	}

}
