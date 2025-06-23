package com.common.data.authentication.entity;

import com.common.data.base.entity.UserRole;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Role {
    public Integer roleId;
    public Boolean read;
    public Boolean write;
    public Boolean update;
    public Boolean delete;

    public List<UserRole> getPermissions() {
        List<UserRole> permissions = new ArrayList<>();
        if (Boolean.TRUE.equals(read)) permissions.add(UserRole.READ);
        if (Boolean.TRUE.equals(write)) permissions.add(UserRole.WRITE);
        if (Boolean.TRUE.equals(update)) permissions.add(UserRole.UPDATE);
        if (Boolean.TRUE.equals(delete)) permissions.add(UserRole.DELETE);
        return permissions;
    }
}
