package com.authService.AuthenticatorService.bootstrap;

import com.authService.AuthenticatorService.model.User;
import com.authService.AuthenticatorService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppBootStrap implements CommandLineRunner {
    private UserRepository userRepository;

    @Autowired
    public AppBootStrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("Jack", "pass123");
        User user2 = new User("Alice", "pass123");
        userRepository.save(user1);
        userRepository.save(user2);
    }
}