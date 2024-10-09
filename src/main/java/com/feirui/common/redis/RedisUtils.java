package com.feirui.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * RedisUtil工具类
 */
@Component
@Slf4j
public class RedisUtils {

    private static final String CACHE_KEY_SEPARATOR = ".";
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 构建缓存key
     */
    public String buildKey(String... strObjs) {
        return String.join(CACHE_KEY_SEPARATOR, strObjs);
    }

    /**
     * 是否存在key
     */
    public boolean exist(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除key
     */
    public boolean del(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * set(不带过期)
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * set(带过期)
     */
    public void setEX(String key, String value, Long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * set(带过期)
     */
    public boolean setNX(String key, String value, Long time, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit));
    }

    /**
     * 获取string类型缓存
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
        } catch (Exception e) {
            log.error("hash表中放入数据异常，key = " + key, e);
        }
    }

}
