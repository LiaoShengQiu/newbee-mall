package com.example.newbeemall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 设置String键值对
     * @param key
     * @param value
     * @param millis
     */
    public void put(String key, Object value, long millis) {
        redisTemplate.opsForValue().set(key, value, millis, TimeUnit.MINUTES);
    }
    public void put(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    public void putForHash(String objectKey, String hkey, String value) {
        redisTemplate.opsForHash().put(objectKey, hkey, value);
    }
    public <T> T get(String key, Class<T> type) {
        return (T) redisTemplate.boundValueOps(key).get();
    }
    public void remove(String key) {
        redisTemplate.delete(key);
    }
    public boolean expire(String key, long millis) {
        return redisTemplate.expire(key, millis, TimeUnit.MILLISECONDS);
    }
    public boolean persist(String key) {
        return redisTemplate.hasKey(key);
    }
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
    public Integer getInteger(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }
    public Long getLong(String key) {
        return (Long) redisTemplate.opsForValue().get(key);
    }
    public Date getDate(String key) {
        return (Date) redisTemplate.opsForValue().get(key);
    }

    /**
     * 对指定key的键值减一
     * @param key
     * @return
     */
    public Long decrBy(String key) {
       Long num = redisTemplate.opsForValue().decrement(key);
        System.out.println("减一后"+num);
      //  System.out.println(Integer.parseInt(redisTemplate.opsForValue().get(key).toString())+1);
        return num;
    }

    /**
     * 对指定key的键值加一
     * @param key
     * @return
     */
    public Long addBy(String key) {
        Long longValue = redisTemplate.opsForValue().increment(key, 1);
        System.out.println("加一后"+longValue);
        return longValue;
    }

    /**
     * 查找key是否存在
     * @param key
     * @return
     */
    public Object isExist(String key){
        return  redisTemplate.opsForValue().get(key);
    }
}