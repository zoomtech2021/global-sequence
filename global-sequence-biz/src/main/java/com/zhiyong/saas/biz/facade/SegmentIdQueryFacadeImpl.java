package com.zhiyong.saas.biz.facade;

import com.zhiyong.saas.biz.service.SegmentIdService;
import com.zhiyong.saas.common.entity.ApiResponse;
import com.zhiyong.saas.common.entity.SegmentId;
import com.zhiyong.saas.facade.api.SegmentIdQueryFacade;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;

/**
 * @ClassName SegmentIdQueryFacadeImpl
 * @Description: 全局sequence号段获取接口
 * @Author 毛军锐
 * @Date 2020/11/19 下午3:55
 **/
@Service(interfaceClass = SegmentIdQueryFacade.class, version = "1.0.0")
public class SegmentIdQueryFacadeImpl implements SegmentIdQueryFacade {

    @Resource
    private SegmentIdService segmentIdService;

    @Override
    public ApiResponse<SegmentId> getNextSegment(String bizType) {
        if (StringUtils.isBlank(bizType)) {
            return ApiResponse.buildCommonErrorResponse("The bizType can not empty!");
        }
        SegmentId segmentId = null;
        try {
            segmentId = segmentIdService.getNextSegmentId(bizType);
        } catch (Exception e) {
            return ApiResponse.buildCommonErrorResponse(e.getMessage());
        }
        return ApiResponse.buildSuccessResponse(segmentId);
    }
}
