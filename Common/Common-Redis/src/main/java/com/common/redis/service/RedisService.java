package com.common.redis.service;

public interface RedisService {
    void setString(String key, Object value);

    void setString(String key, Object value, Integer expire);
}
