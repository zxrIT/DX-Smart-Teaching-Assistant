package com.authentication.service.repository;

import com.authentication.service.entity.RoleAuthentication;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
@DS("master_user")
public interface RoleAuthenticationRepository extends BaseMapper<RoleAuthentication> {
}
