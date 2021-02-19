package com.zhiyong.saas.facade.api;

import com.zhiyong.saas.common.entity.ApiResponse;
import com.zhiyong.saas.common.entity.SegmentId;

/**
 * @ClassName SegmentIdQueryFacade
 * @Description: TODO
 * @Author 毛军锐
 * @Date 2020/11/19 下午3:49
 **/
public interface SegmentIdQueryFacade {

    /**
     * 获取下一个号段
     * @param bizType
     * @return
     */
    ApiResponse<SegmentId> getNextSegment(String bizType);
}
