package com.common.rabbitmq.entity;

import lombok.Data;

@Data
public class RenewalTokenEntity {
    private String userId;
    private String token;
}
