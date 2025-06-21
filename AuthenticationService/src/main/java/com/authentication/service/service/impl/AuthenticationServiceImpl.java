package com.authentication.service.service.impl;

import com.authentication.service.repository.RoleAuthenticationRepository;
import com.authentication.service.repository.UserAuthenticationRepository;
import com.authentication.service.service.AuthenticationService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.common.data.authentication.exception.AuthenticateException;
import com.common.data.base.entity.BaseResponse;
import com.common.data.base.entity.BaseResponseContent;
import com.common.utils.JsonSerialization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final UserAuthenticationRepository authenticationRepository;
    private final RoleAuthenticationRepository roleAuthenticationRepository;

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public String authenticate(String account, String password) throws AuthenticateException {
        try {

            return JsonSerialization.toJson(new BaseResponse<String>(
                    BaseResponseContent.SUCCESS_CODE, BaseResponseContent.SUCCESS_MESSAGE, "登录成功"
            ));
        } catch (Exception exception) {
            throw new AuthenticateException(exception.getMessage());
        }
    }
}
