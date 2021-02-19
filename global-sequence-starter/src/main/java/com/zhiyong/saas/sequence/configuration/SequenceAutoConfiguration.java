package com.zhiyong.saas.sequence.configuration;

import com.zhiyong.saas.facade.api.SegmentIdQueryFacade;
import com.zhiyong.saas.sequence.factory.impl.SequenceIdGeneratorFactory;
import com.zhiyong.saas.sequence.model.SequenceId;
import com.zhiyong.saas.sequence.properties.SequenceProperties;
import javax.annotation.Resource;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SequenceAutoConfiguration
 * @Description: TODO
 * @Author 毛军锐
 * @Date 2020/11/19 下午7:17
 **/
@ConditionalOnClass(SequenceIdGeneratorFactory.class)
@EnableConfigurationProperties(value = {SequenceProperties.class})
@Configuration
public class SequenceAutoConfiguration {

    @Resource
    private SequenceProperties sequenceProperties;

    @Reference(version = "1.0.0", timeout = 1500)
    private SegmentIdQueryFacade segmentIdQueryFacade;

    /**
     * @return
     */
    @Bean
    public SequenceIdGeneratorFactory sequenceIdGeneratorFactory() {
        return new SequenceIdGeneratorFactory(segmentIdQueryFacade);
    }

    /**
     * 全局sequenceId初始化
     *
     * @param sequenceIdGeneratorFactory
     * @return
     */
    @Bean
    public SequenceId sequenceId(SequenceIdGeneratorFactory sequenceIdGeneratorFactory) {
        return new SequenceId(sequenceIdGeneratorFactory, sequenceProperties.getName());
    }
}
