package com.common.redis.content;

import lombok.Data;

@Data
public class RedisAuthenticationConstant {
    public final static String redisAuthenticationKey = "DX:Smart:Teaching:Assistant:token:";
    public final static Integer redisAuthenticationExpire = 60 * 60;
}
