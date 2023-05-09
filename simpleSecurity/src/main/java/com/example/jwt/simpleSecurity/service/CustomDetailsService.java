//package com.example.jwt.simpleSecurity.service;
//
//import com.example.jwt.simpleSecurity.model.CustomUser;
//import com.example.jwt.simpleSecurity.model.User;
//import com.example.jwt.simpleSecurity.repository.OAuthDao;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@Slf4j
//public class CustomDetailsService implements UserDetailsService {
//
//    @Autowired
//    OAuthDao oauthDao;
//
//    @Override
//    public CustomUser loadUserByUsername(final String username) throws UsernameNotFoundException {
//        User userEntity = null;
//        try {
//            userEntity = oauthDao.getUserDetails(username);
//            return new CustomUser(userEntity);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new UsernameNotFoundException("User " + username + " was not found in the database");
//        }
//    }
//}