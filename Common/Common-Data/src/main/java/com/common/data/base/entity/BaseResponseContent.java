package com.common.data.base.entity;

import lombok.Data;

@Data
public class BaseResponseContent {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer SUCCESS_CREATED_CODE = 201;
    public static final Integer BAD_REQUEST_CODE = 400;
    public static final Integer BAD_REQUEST_UNAUTHORIZED_CODE = 401;
    public static final Integer FORBIDDEN_CODE = 403;
    public static final Integer NOT_FOUND_CODE = 404;
    public static final Integer INTERNAL_SERVER_ERROR_CODE = 500;
    public static final Integer BAD_GATEWAY_ERROR_CODE = 502;
    public static final Integer SERVICE_UNAVAILABLE_ERROR_CODE = 503;
    public static final String SUCCESS_MESSAGE = "SUCCESS";
    public static final String SUCCESS_CREATED_MESSAGE = "CREATED_MESSAGE";
    public static final String BAD_REQUEST_MESSAGE = "BAD_REQUEST";
    public static final String BAD_REQUEST_UNAUTHORIZED_MESSAGE = "UNAUTHORIZED_MESSAGE";
    public static final String FORBIDDEN_MESSAGE = "FORBIDDEN_MESSAGE";
    public static final String NOT_FOUND_MESSAGE = "NOT_FOUND_MESSAGE";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR_MESSAGE";
    public static final String BAD_GATEWAY_ERROR_MESSAGE = "BAD_GATEWAY_ERROR_MESSAGE";
    public static final String SERVICE_UNAVAILABLE_ERROR_MESSAGE = "SERVICE_UNAVAILABLE_ERROR_MESSAGE";
}
