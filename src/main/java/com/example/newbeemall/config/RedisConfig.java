package com.example.newbeemall.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;



@Configuration
@EnableCaching
public class RedisConfig  extends CachingConfigurerSupport {
    // 配置redis得配置详解
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 自定义生成key的规则
     * 缓存对象集合中，缓存是以 key-value 形式保存的。
     * 当不指定缓存的 key 时，SpringBoot 会使用 SimpleKeyGenerator 生成 key
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {

        return new KeyGenerator() {
            public Object generate(Object target, Method method, Object... params) {
                //格式化缓存key字符串
                StringBuilder sb = new StringBuilder();
                //追加类名
                sb.append(target.getClass().getName());
                //追加方法名
                sb.append(method.getName());
                //遍历参数并且追加
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
              //  System.out.println("调用Redis缓存Key : " + sb.toString());
                return sb.toString();
            }
        };
    }

}