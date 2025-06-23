package com.authentication.service.controller;

import com.authentication.service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@SuppressWarnings("all")
@RequiredArgsConstructor
@Tag(name = "AuthenticationController", description = "认证相关操作")
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;

    @GetMapping("/login/{string}")
    public String login(@PathVariable String string) {
        return "11";
    }
}
