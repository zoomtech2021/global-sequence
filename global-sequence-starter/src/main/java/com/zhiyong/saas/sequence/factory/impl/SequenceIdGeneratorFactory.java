package com.zhiyong.saas.sequence.factory.impl;

import com.zhiyong.saas.facade.api.SegmentIdQueryFacade;
import com.zhiyong.saas.sequence.factory.AbstractIdGeneratorFactory;
import com.zhiyong.saas.sequence.generator.IdGenerator;
import com.zhiyong.saas.sequence.generator.impl.CachedIdGenerator;

public class SequenceIdGeneratorFactory extends AbstractIdGeneratorFactory {
    private SegmentIdQueryFacade segmentIdQueryFacade;

    public SequenceIdGeneratorFactory(SegmentIdQueryFacade segmentIdQueryFacade) {
        this.segmentIdQueryFacade = segmentIdQueryFacade;
    }

    @Override
    protected IdGenerator createIdGenerator(String bizType) {
        return new CachedIdGenerator(bizType, segmentIdQueryFacade);
    }
}
