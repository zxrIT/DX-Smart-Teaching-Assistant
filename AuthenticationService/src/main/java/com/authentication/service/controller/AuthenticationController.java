package com.authentication.service.controller;

import com.authentication.service.request.LoginParam;
import com.authentication.service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("all")
@RequiredArgsConstructor
@Tag(name = "AuthenticationController", description = "提供认证相关操作")
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @GetMapping("/getEncryptionPassword/{password}")
    @Operation(summary = "获取用户经系统加密后的密码")
    public String getEncryptionPassword(@PathVariable String password) {
        return authenticationService.getEncryptionPassword(password);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public String login(@RequestBody LoginParam loginParam) {
        return authenticationService.authenticate(loginParam.getAccount(), loginParam.getPassword());
    }

    @GetMapping("/logout/{account}")
    @Operation(summary = "用户退出登录")
    public String logout(@Parameter(description = "退出登录的用户", required = true) @PathVariable String account) {
        return authenticationService.logout(account);
    }
}
