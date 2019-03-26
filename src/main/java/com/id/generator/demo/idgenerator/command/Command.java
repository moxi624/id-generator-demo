package com.id.generator.demo.idgenerator.command;

import com.id.generator.demo.idgenerator.services.RedisIdWorker;
import com.id.generator.demo.idgenerator.services.SnowflakeIdWorker;
import com.id.generator.demo.idgenerator.services.UuidIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟高并发下，使用不同Id策略生成唯一标识符Id
 *
 * @Author daituo
 * @Date 2019-3-26
 **/
@Component
public class Command implements CommandLineRunner {

    private int threadNum = 100;

    private CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    @Autowired
    private UuidIdGenerator uuidIdGenerator;
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    @Autowired
    private RedisIdWorker redisIdWorker;

    @Override
    public void run(String... strings) throws Exception {
        Thread[] threads = new Thread[threadNum];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(() -> {
                try {
                    countDownLatch.await();
                    /** 使用UUID生成ID唯一标识符 */
                    //uuidIdGenerator.generatorId();

                    /** 使用雪花算法生成ID唯一标识符 */
                    //long id = snowflakeIdWorker.nextId();

                    /** 使用redis计数器生成ID唯一标识符 */
                    Long id = redisIdWorker.nextId();
                    System.out.println(id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            thread.start();
            countDownLatch.countDown();

        }
    }
}
