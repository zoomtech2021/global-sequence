package com.zhiyong.saas.sequence.factory;


import com.zhiyong.saas.sequence.generator.IdGenerator;

public interface IdGeneratorFactory {

    /**
     * 根据bizType创建id生成器
     * @param bizType
     * @return
     */
    IdGenerator getIdGenerator(String bizType);
}
