package com.authentication.service.request;

import lombok.Data;

@Data
public class LoginParam {
    private String account;
    private String password;
}
