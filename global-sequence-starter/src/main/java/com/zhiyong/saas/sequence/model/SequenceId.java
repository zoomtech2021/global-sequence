package com.zhiyong.saas.sequence.model;

import com.zhiyong.saas.sequence.factory.impl.SequenceIdGeneratorFactory;
import com.zhiyong.saas.sequence.generator.IdGenerator;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 全局sequence对象
 */
public class SequenceId {

    private SequenceIdGeneratorFactory factory;
    private String defaultBizType;

    /**
     * @param factory
     */
    public SequenceId(SequenceIdGeneratorFactory factory, String defaultBizType) {
        this.factory = factory;
        this.defaultBizType = defaultBizType;
    }

    /**
     * 获取一个全局默认sequenceId
     *
     * @return
     */
    public Long next() {
        return next(defaultBizType);
    }

    /**
     * 批量获取全局默认sequenceId
     *
     * @param batchSize
     * @return
     */
    public List<Long> next(Integer batchSize) {
        return next(defaultBizType, batchSize);
    }

    /**
     * 获取一个sequenceId
     *
     * @param bizType
     * @return
     */
    public Long next(String bizType) {
        if (StringUtils.isBlank(bizType)) {
            throw new IllegalArgumentException("sequence name is null");
        }
        IdGenerator idGenerator = factory.getIdGenerator(bizType);
        return idGenerator.nextId();
    }

    /**
     * 批量获取sequenceId
     *
     * @param bizType
     * @param batchSize
     * @return
     */
    public List<Long> next(String bizType, Integer batchSize) {
        if (StringUtils.isBlank(bizType)) {
            throw new IllegalArgumentException("sequence name is null");
        }
        IdGenerator idGenerator = factory.getIdGenerator(bizType);
        return idGenerator.nextId(batchSize);
    }
}
