package com.authentication.service.service;

public interface AuthenticationService {
    String getEncryptionPassword(String password);

    String authenticate(String account, String password);

    String logout(String account);
}
