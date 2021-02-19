package com.zhiyong.saas.dal.dao.impl;

import com.zhiyong.saas.dal.dao.TinyIdInfoDao;
import com.zhiyong.saas.dal.dao.entity.TinyIdInfo;
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
public class TinyIdInfoDaoImpl implements TinyIdInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public TinyIdInfo queryByBizType(String bizType) {
        String sql = "select id, biz_type, begin_id, max_id, step, delta, remainder, create_time, update_time, version"
                   + " from tiny_id_info where biz_type = ?";
        List<TinyIdInfo> list = jdbcTemplate.query(sql, new Object[]{bizType}, new TinyIdInfoRowMapper());
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public int updateMaxId(Long id, Long newMaxId, Long oldMaxId, Long version, String bizType) {
        String sql = "update tiny_id_info set max_id= ?, update_time=now(), version=version+1"
                   + " where id=? and max_id=? and version=? and biz_type=?";
        return jdbcTemplate.update(sql, newMaxId, id, oldMaxId, version, bizType);
    }


    public static class TinyIdInfoRowMapper implements RowMapper<TinyIdInfo> {

        @Override
        public TinyIdInfo mapRow(ResultSet resultSet, int row) throws SQLException {
            TinyIdInfo tinyIdInfo = new TinyIdInfo();
            tinyIdInfo.setId(resultSet.getLong("id"));
            tinyIdInfo.setBizType(resultSet.getString("biz_type"));
            tinyIdInfo.setBeginId(resultSet.getLong("begin_id"));
            tinyIdInfo.setMaxId(resultSet.getLong("max_id"));
            tinyIdInfo.setStep(resultSet.getInt("step"));
            tinyIdInfo.setDelta(resultSet.getInt("delta"));
            tinyIdInfo.setRemainder(resultSet.getInt("remainder"));
            tinyIdInfo.setCreateTime(resultSet.getDate("create_time"));
            tinyIdInfo.setUpdateTime(resultSet.getDate("update_time"));
            tinyIdInfo.setVersion(resultSet.getLong("version"));
            return tinyIdInfo;
        }
    }
}
