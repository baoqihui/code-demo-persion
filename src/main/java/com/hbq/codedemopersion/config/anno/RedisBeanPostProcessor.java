package com.hbq.codedemopersion.config.anno;

import cn.hutool.core.util.ObjectUtil;
import com.hbq.codedemopersion.util.RedisUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
/**
 * @Author: huibq
 * @Date: 2022/4/28 10:11
 * @Description: 通过Bean后置处理器自定义注解配置redis
 */
@Component
public class RedisBeanPostProcessor implements BeanPostProcessor {
    @Resource
    Environment environment;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /**
         * 利用Java反射机制注入属性
         */
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            DB annotation = declaredField.getAnnotation(DB.class);
            if (null == annotation) {
                continue;
            }
            //从环境变量中获取值
            String dbKey = ObjectUtil.defaultIfEmpty(annotation.value(), "${spring.redis.database}");
            String dbIndex = environment.resolvePlaceholders(dbKey);
            RedisUtils redisUtils = new RedisUtils(host, password, port, dbIndex);
            try {
                declaredField.setAccessible(true);
                declaredField.set(bean, redisUtils);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o; //这里要返回o，不然启动时会报错
    }
}