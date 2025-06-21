package com.common.data.authentication.entity;

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

    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        if (Boolean.TRUE.equals(read)) permissions.add("read");
        if (Boolean.TRUE.equals(write)) permissions.add("write");
        if (Boolean.TRUE.equals(update)) permissions.add("update");
        if (Boolean.TRUE.equals(delete)) permissions.add("delete");
        return permissions;
    }
}
