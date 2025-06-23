package com.common.component.exception;

import com.common.data.base.entity.BaseResponse;
import com.common.data.base.entity.BaseResponseContent;
import com.common.utils.JsonSerialization;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandle(Exception exception) {
        return JsonSerialization.toJson(new BaseResponse<String>(
                BaseResponseContent.SERVICE_UNAVAILABLE_ERROR_CODE, BaseResponseContent.SERVICE_UNAVAILABLE_ERROR_MESSAGE,
                "服务端错误请联系管理员处理"
        ));
    }
}
