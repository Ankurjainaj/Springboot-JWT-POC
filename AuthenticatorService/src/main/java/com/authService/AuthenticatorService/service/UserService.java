package com.authService.AuthenticatorService.service;

import com.authService.AuthenticatorService.exception.UserNotFoundException;
import com.authService.AuthenticatorService.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void saveUser(User user);

    User getUserByNameAndPassword(String name, String password) throws UserNotFoundException;
}
