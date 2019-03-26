package com.id.generator.demo.idgenerator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 利用redis特性：单线程原子操作，自增计数器API来实现唯一的ID,increment(key,delta) API用于将key值进行递增，delta表示递增步长，并返回增长数值，
 *      如果key不存在，则创建并赋值0
 *
 * 优点：1.可方便结合业务处理，生成ID唯一标识符
 *      2.利用Redis操作原子性，保证高并发的时候不会产生重复
 *
 * 缺点：1.引入了第三方依赖
 *      2.增加了一次网络开销
 *      3.需要对redis实现高可用
 *
 * @Author daituo
 * @Date 2019-3-26
 **/
@Component
public class RedisIdWorker {

    private static final String key = "redis:id:generator";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /** 生成ID */
    public Long nextId() {
        Long prefix = getPrefix();
        Long increment = stringRedisTemplate.opsForValue().increment(key, 1L);
        /**
         * String.format(pattern,arg...)
         * pattern = %[index$][标识]*[最小宽度]转换符
         * %1$06d表示：把数值按照最小6位宽度，不足6位需要补0
         */
        return Long.parseLong(prefix + String.format("%1$06d",increment));
    }


    private Long getPrefix() {
        /** 使用jdk1.8 LocalDateTime 日期时间对象*/
        LocalDateTime dateTime = LocalDateTime.now();
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        return Long.parseLong("" + year + month + day + hour);

    }
}
