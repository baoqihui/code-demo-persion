package com.hbq.codedemopersion.common.service.impl;

import com.hbq.codedemopersion.common.service.RedisService;
import com.hbq.codedemopersion.redis_lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @DistributedLock(keyPrefix = "test:redis", keys = "#order")
    @Override
    public void doSomething(String order) {
        log.info("orderId:{}，获取到了锁", order);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("orderId:{}，释放了锁", order);
    }
}
