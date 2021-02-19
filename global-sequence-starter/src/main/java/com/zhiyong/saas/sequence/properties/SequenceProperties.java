package com.zhiyong.saas.sequence.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName SequenceProperties
 * @Description: TODO
 * @Author 毛军锐
 * @Date 2020/11/19 下午7:21
 **/
@ConfigurationProperties(prefix = "zhiyong.default.sequence")
public class SequenceProperties {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
