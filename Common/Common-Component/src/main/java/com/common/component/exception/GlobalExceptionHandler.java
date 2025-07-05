package com.common.component.exception;

import com.common.data.authentication.exception.ForbiddenException;
import com.common.data.base.entity.BaseResponse;
import com.common.data.base.entity.BaseResponseContent;
import com.common.utils.JsonSerialization;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandle(Exception exception) {
        if (exception instanceof ForbiddenException) {
            return JsonSerialization.toJson(new BaseResponse<String>(
                    BaseResponseContent.FORBIDDEN_CODE, BaseResponseContent.FORBIDDEN_MESSAGE, "您的登录已过时或没有登录，请登录后再使用"
            ));
        }
        return JsonSerialization.toJson(new BaseResponse<String>(
                BaseResponseContent.SERVICE_UNAVAILABLE_ERROR_CODE, BaseResponseContent.SERVICE_UNAVAILABLE_ERROR_MESSAGE,
                "服务端错误请联系管理员处理"
        ));
    }
}
