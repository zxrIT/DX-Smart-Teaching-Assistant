package com.authentication.service.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.common.data.authentication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserAuthentication extends User {
}
