package com.common.data.authentication.entity;

import lombok.Data;

@Data
public class User {
    public String userId;
    public String userName;
    public String account;
    public String password;
    public Integer role;
}
