package com.zhiyong.saas.dal.dao.impl;

import com.zhiyong.saas.dal.dao.TinyIdTokenDao;
import com.zhiyong.saas.dal.dao.entity.TinyIdToken;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * @author du_imba
 */
@Repository
public class TinyIdTokenDaoImpl implements TinyIdTokenDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TinyIdToken> selectAll() {
        String sql = "select id, token, biz_type, remark, create_time, update_time from tiny_id_token";
        return jdbcTemplate.query(sql, new TinyIdTokenRowMapper());
    }

    public static class TinyIdTokenRowMapper implements RowMapper<TinyIdToken> {

        @Override
        public TinyIdToken mapRow(ResultSet resultSet, int row) throws SQLException {
            TinyIdToken token = new TinyIdToken();
            token.setId(resultSet.getInt("id"));
            token.setToken(resultSet.getString("token"));
            token.setBizType(resultSet.getString("biz_type"));
            token.setRemark(resultSet.getString("remark"));
            token.setCreateTime(resultSet.getDate("create_time"));
            token.setUpdateTime(resultSet.getDate("update_time"));
            return token;
        }
    }
}
