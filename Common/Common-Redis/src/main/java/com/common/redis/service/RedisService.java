package com.common.redis.service;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void setString(String key, Object value);

    void setString(String key, Object value, Integer expire, TimeUnit unit);

    void removeString(String key);

    String getString(String key);
}
