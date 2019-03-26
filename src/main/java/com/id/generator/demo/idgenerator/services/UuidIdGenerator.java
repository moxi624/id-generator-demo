package com.id.generator.demo.idgenerator.services;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 分布式集群下使用UUID生成唯一ID，例如订单ID
 *
 * 优点：使用简单，不依赖组件，不影响数据库扩展
 * 缺点：1.数据库索引效率低，因为生成的是36位字符串，数据库对排序后的数值型构建索引效率高
 *      2.过于无意义
 *      3.36位字符串，占用数据库空间大
 *      4.集群环境下，会有重复的几率
 *
 * @Author daituo
 * @Date 2019-3-26
 **/
@Service
public class UuidIdGenerator {

    public String generatorId() {
        return UUID.randomUUID().toString();
    }
}
