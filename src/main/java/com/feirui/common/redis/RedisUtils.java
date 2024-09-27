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

    public Boolean zAdd(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, Double.parseDouble(String.valueOf(score)));
    }

    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    public Set<Object> rangeZset(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    public void removeZsetList(String key, Set<String> value) {
        value.forEach((val) -> redisTemplate.opsForZSet().remove(key, val));
    }

    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Set<Object> rangeByScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, Double.parseDouble(String.valueOf(start)), Double.parseDouble(String.valueOf(end)));
    }

    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }

}
