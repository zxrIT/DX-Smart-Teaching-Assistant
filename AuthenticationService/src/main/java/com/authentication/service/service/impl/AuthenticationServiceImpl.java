package com.authentication.service.service.impl;

import com.authentication.service.entity.RoleAuthentication;
import com.authentication.service.entity.UserAuthentication;
import com.authentication.service.repository.RoleAuthenticationRepository;
import com.authentication.service.repository.UserAuthenticationRepository;
import com.authentication.service.service.AuthenticationService;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.component.component.TokenManager;
import com.common.data.authentication.exception.AuthenticateException;
import com.common.data.base.entity.BaseResponse;
import com.common.data.base.entity.BaseResponseContent;
import com.common.data.base.entity.UserRole;
import com.common.redis.content.RedisAuthenticationConstant;
import com.common.redis.service.RedisService;
import com.common.utils.Encryption;
import com.common.utils.JsonSerialization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final UserAuthenticationRepository userAuthenticationRepository;
    private final RoleAuthenticationRepository roleAuthenticationRepository;
    private final RedisService redisService;
    private final TokenManager tokenManager;

    @Override
    public String getEncryptionPassword(String password) throws RuntimeException {
        try {
            return JsonSerialization.toJson(new BaseResponse<String>(
                    BaseResponseContent.SUCCESS_CODE, BaseResponseContent.SUCCESS_MESSAGE, Encryption.encryptToMd5(password)
            ));
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public String authenticate(String account, String password) throws AuthenticateException {
        try {
            UserAuthentication userAuthentication = userAuthenticationRepository
                    .selectOne(new LambdaQueryWrapper<UserAuthentication>().eq(UserAuthentication::getAccount, account));
            if (userAuthentication == null) {
                throw new AuthenticateException("账号不存在");
            }
            if (!Encryption.encryptToMd5(password).equals(userAuthentication.getPassword())) {
                throw new AuthenticateException("密码错误");
            }
            List<UserRole> permissions = roleAuthenticationRepository.selectOne(new LambdaQueryWrapper<RoleAuthentication>().eq(
                    RoleAuthentication::getRoleId, userAuthentication.getRole()
            )).getPermissions();
            String userToken = tokenManager.createToken(userAuthentication.getUserId(), userAuthentication.getUserName()
                    , userAuthentication.getRole(), permissions);
            redisService.setString(RedisAuthenticationConstant.redisAuthenticationKey + userAuthentication.getUserId(), userToken,
                    RedisAuthenticationConstant.redisAuthenticationExpire, TimeUnit.SECONDS);
            return JsonSerialization.toJson(new BaseResponse<String>(
                    BaseResponseContent.SUCCESS_CODE, BaseResponseContent.SUCCESS_MESSAGE, "登录成功"
            ));
        } catch (Exception exception) {
            throw new AuthenticateException(exception.getMessage());
        }
    }

    @Override
    @DSTransactional(rollbackFor = Exception.class)
    public String logout(String account) throws RuntimeException {
        try {
            redisService.removeString(RedisAuthenticationConstant.redisAuthenticationKey + account);
            return JsonSerialization.toJson(new BaseResponse<String>(
                    BaseResponseContent.SUCCESS_CODE, BaseResponseContent.SUCCESS_MESSAGE, "退出成功"
            ));
        } catch (Exception exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
