package com.common.component.aspect;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.common.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
public class HttpRequestLoggerAspect {
    @Value("${spring.application.name}")
    private String applicationName;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void logger() {
    }

    @Around("logger()")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = simpleDateFormat.format(new Date());
        StringBuffer url = request.getRequestURL();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        long startTime = System.currentTimeMillis();
        Map loggerMap = new HashMap();
        loggerMap.put("currentTime", startTime);
        loggerMap.put("class_method", class_method);
        loggerMap.put("url", url);
        loggerMap.put("method", method);
        loggerMap.put("applicationName", applicationName);
        loggerMap.put("ip", ip);
        List list = new ArrayList();
        Map<String, Object> map = new HashMap();
        for (int i = 0; i < argNames.length; i++) {
            String argName = argNames[i];
            String argValue = "";
            if (args[i] != null) {
                argValue = args[i].toString();
            } else {
                argValue = null;
            }
            map.put(argName, argValue);
        }
        loggerMap.put("request", map);
        Object result = null;
        try {
            result = joinPoint.proceed();
            loggerMap.put("response", JsonUtil.objectToJson(result));
        } catch (Throwable throwable) {
            loggerMap.put("exception", ExceptionUtil.getRootCauseMessage(throwable));
            throw throwable;
        } finally {
            loggerMap.put("cost", System.currentTimeMillis() - startTime);
            loggerMap.put("endTime", System.currentTimeMillis());
            log.info(JsonUtil.objectToJson(loggerMap) );
        }
        return result;
    }
}
