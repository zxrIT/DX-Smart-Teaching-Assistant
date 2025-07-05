package com.common.component.annotation;

import com.common.component.aspect.HttpRequestLoggerAspect;
import com.common.component.component.TokenManager;
import com.common.component.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({HttpRequestLoggerAspect.class, GlobalExceptionHandler.class, TokenManager.class})
public @interface EnableAutoCommonComponentBeans {
}
