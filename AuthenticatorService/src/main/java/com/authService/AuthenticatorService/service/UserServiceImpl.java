package com.authService.AuthenticatorService.service;

import com.authService.AuthenticatorService.exception.UserNotFoundException;
import com.authService.AuthenticatorService.model.User;
import com.authService.AuthenticatorService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) throws UserNotFoundException {
        User user = userRepository.findByUserNameAndPassword(name, password);
        if (user == null) {
            throw new UserNotFoundException("Invalid id and password");
        }
        return user;
    }
}