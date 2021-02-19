package com.zhiyong.saas.dal.dao;

import com.zhiyong.saas.dal.dao.entity.TinyIdToken;
import java.util.List;

/**
 * @author du_imba
 */
public interface TinyIdTokenDao {
    /**
     * 查询db中所有的token信息
     * @return
     */
    List<TinyIdToken> selectAll();
}
