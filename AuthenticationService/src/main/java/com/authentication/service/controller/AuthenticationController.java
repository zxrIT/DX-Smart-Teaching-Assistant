package com.authentication.service.controller;

import com.authentication.service.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("all")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
}
