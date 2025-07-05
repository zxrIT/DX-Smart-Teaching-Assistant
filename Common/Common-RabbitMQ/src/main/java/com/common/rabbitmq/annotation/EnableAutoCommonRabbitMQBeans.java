package com.common.rabbitmq.annotation;

import com.common.rabbitmq.configuration.MessageConverterConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MessageConverterConfiguration.class})
public @interface EnableAutoCommonRabbitMQBeans {
}
