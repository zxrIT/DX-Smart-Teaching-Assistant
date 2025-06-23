package com.common.redis.service.impl;

import com.common.redis.service.RedisService;
import com.common.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    @Autowired
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void setString(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.objectToJson(value));
    }

    @Override
    public void setString(String key, Object value, Integer expire, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.objectToJson(value), expire, unit);
    }

    @Override
    public void removeString(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public String getString(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
