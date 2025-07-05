package com.common.redis.annotation;

import com.common.redis.service.impl.RedisServiceImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisServiceImpl.class})
public @interface EnableAutoCommonRedisBeans {
}
