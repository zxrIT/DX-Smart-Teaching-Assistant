package com.authentication.service.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.common.data.authentication.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("role")
public class RoleAuthentication extends Role {
    @TableId(type = IdType.AUTO)
    public Integer roleId;
    @TableField("`read`")
    public Boolean read;
    @TableField("`write`")
    public Boolean write;
    @TableField("`update`")
    public Boolean update;
    @TableField("`delete`")
    public Boolean delete;
}
