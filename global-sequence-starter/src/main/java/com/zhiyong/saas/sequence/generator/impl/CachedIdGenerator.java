package com.zhiyong.saas.sequence.generator.impl;

import com.zhiyong.saas.common.entity.ApiResponse;
import com.zhiyong.saas.common.entity.Result;
import com.zhiyong.saas.common.entity.ResultCode;
import com.zhiyong.saas.common.entity.SegmentId;
import com.zhiyong.saas.common.exception.TinyIdSysException;
import com.zhiyong.saas.facade.api.SegmentIdQueryFacade;
import com.zhiyong.saas.sequence.factory.NamedThreadFactory;
import com.zhiyong.saas.sequence.generator.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author du_imba
 */
public class CachedIdGenerator implements IdGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CachedIdGenerator.class);
    private String bizType;
    private SegmentIdQueryFacade segmentIdQueryFacade;
    private volatile SegmentId current;
    private volatile SegmentId next;
    private volatile boolean isLoadingNext;
    private Object lock = new Object();
    private ExecutorService executorService =
            Executors.newSingleThreadExecutor(new NamedThreadFactory("tinyid-generator"));

    /**
     * @param bizType
     * @param segmentIdQueryFacade
     */
    public CachedIdGenerator(String bizType, SegmentIdQueryFacade segmentIdQueryFacade) {
        this.bizType = bizType;
        this.segmentIdQueryFacade = segmentIdQueryFacade;
        loadCurrent();
    }

    private synchronized void loadCurrent() {
        if (current == null || !current.useful()) {
            if (next == null) {
                SegmentId segmentId = querySegmentId();
                this.current = segmentId;
            } else {
                current = next;
                next = null;
            }
        }
    }

    private SegmentId querySegmentId() {
        String message = null;
        try {
            LOGGER.info("============The local segmentId is gone, invoke once remote segmentId!");
            ApiResponse<SegmentId> response = segmentIdQueryFacade.getNextSegment(bizType);
            if (!response.isSuccess()) {
                throw new TinyIdSysException("error query segmentId: " + response.getMsg());
            }
            return response.getData();
        } catch (Exception e) {
            message = e.getMessage();
        }
        throw new TinyIdSysException("error query segmentId: " + message);
    }

    private void loadNext() {
        if (next == null && !isLoadingNext) {
            synchronized (lock) {
                if (next == null && !isLoadingNext) {
                    isLoadingNext = true;
                    executorService.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 无论获取下个segmentId成功与否，都要将isLoadingNext赋值为false
                                next = querySegmentId();
                            } finally {
                                isLoadingNext = false;
                            }
                        }
                    });
                }
            }
        }
    }

    /**
     * @return
     */
    @Override
    public Long nextId() {
        while (true) {
            if (current == null) {
                loadCurrent();
                continue;
            }
            Result result = current.nextId();
            if (result.getCode() == ResultCode.OVER) {
                loadCurrent();
            } else {
                if (result.getCode() == ResultCode.LOADING) {
                    loadNext();
                }
                return result.getId();
            }
        }
    }

    /**
     * @param batchSize
     * @return
     */
    @Override
    public List<Long> nextId(Integer batchSize) {
        if (batchSize == null) {
            batchSize = 1;
        }
        if (batchSize > 250) {
            throw new TinyIdSysException("The batch fetch sequence max size must less than 250! ");
        }
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < batchSize; i++) {
            Long id = nextId();
            ids.add(id);
        }
        return ids;
    }

}
