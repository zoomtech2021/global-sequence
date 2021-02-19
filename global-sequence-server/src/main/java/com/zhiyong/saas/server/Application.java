package com.zhiyong.saas.server;

import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.zhiyong.saas")
@DubboComponentScan(value = {"com.zhiyong.saas.biz.facade"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}