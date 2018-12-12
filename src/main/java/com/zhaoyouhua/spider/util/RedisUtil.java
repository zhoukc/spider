package com.zhaoyouhua.spider.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description create by zhoukc
 */
@Component
public class RedisUtil {

    @Resource(name = "redisCacheTemplate")
    private RedisTemplate redisTemplate;


    public boolean set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * @Description 设置key的存活时间，单位小时
     */
    public boolean setHExpire(String key, Object value, int time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.HOURS);
        return true;
    }

    /**
     * @Description 设置key的存活时间，单位分钟
     */
    public boolean setMinExpire(String key, Object value, int time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
        return true;
    }

    /**
     * @Description 设置key的存活时间，单位秒
     */
    public boolean setSecExpire(String key, Object value, int time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        return true;
    }


    /**
     * @Description 设置key的存活时间，单位天
     */
    public boolean setDayExpire(String key, Object value, int time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.DAYS);
        return true;
    }


    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public <T> T get(String key, Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }


    public boolean del(String key) {
        return redisTemplate.delete(key);
    }


}
