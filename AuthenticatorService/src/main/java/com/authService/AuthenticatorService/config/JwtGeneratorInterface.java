package com.authService.AuthenticatorService.config;

import com.authService.AuthenticatorService.model.User;

import java.util.Map;

public interface JwtGeneratorInterface {

    Map<String, String> generateToken(User user);
}