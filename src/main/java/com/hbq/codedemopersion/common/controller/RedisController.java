package com.hbq.codedemopersion.common.controller;

import cn.hutool.core.util.RandomUtil;
import com.hbq.codedemopersion.common.model.RedisKey;
import com.hbq.codedemopersion.common.model.Result;
import com.hbq.codedemopersion.config.anno.DB;
import com.hbq.codedemopersion.redis_delay_queue.RedisDelayQueueUtil;
import com.hbq.codedemopersion.util.RedisUtils;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static com.hbq.codedemopersion.redis_delay_queue.RedisDelayQueueEnum.ORDER_PAYMENT_TIMEOUT;

/**
 * @Author: huibq
 * @Description: ElasticSearch测试类
 * @Date: 2021/11/22 14:40
 */
@Slf4j
@RestController
@RequestMapping("test/redis")
@AllArgsConstructor
public class RedisController {
    private RedisUtils redisUtils;
    @DB
    private RedisUtils redisUtils2;
    @DB("${spring.redis.database2}")
    private RedisUtils redisUtils3;

    private RedisDelayQueueUtil redisDelayQueueUtil;

    @ApiOperation("测试redis多数据源配置")
    @GetMapping("/toManyDataSource")
    public Result toManyDataSource() {
        redisUtils.set("helloBoy1", "helloBoy");
        redisUtils2.set("helloBoy2", "helloBoy");
        redisUtils3.set("helloBoy3", "helloBoy");
        return Result.succeed("保存成功，请查看redis中的数据");
    }

    @ApiOperation("测试redis发送队列消息")
    @GetMapping("/toQueue")
    public Result toQueue() {
        redisUtils.lpush(RedisKey.REDIS_MESSAGE_LIST_KEY, "helloBoy" + RandomUtil.randomNumbers(1));
        return Result.succeed("发送成功");
    }

    @ApiOperation("测试redis发送延时消息")
    @GetMapping("/toDelayQueue")
    public Result toDelayQueue(String order) {
        redisDelayQueueUtil.addDelayQueue(ORDER_PAYMENT_TIMEOUT.getCode(), order, 10, TimeUnit.SECONDS);
        return Result.succeed("发送成功，延时10秒");
    }
}
