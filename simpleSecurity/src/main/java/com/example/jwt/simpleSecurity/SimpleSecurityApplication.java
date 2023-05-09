package com.example.jwt.simpleSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication()
//exclude = {DataSourceAutoConfiguration.class }
//@EnableAuthorizationServer
//@EnableResourceServer
public class SimpleSecurityApplication extends SpringBootServletInitializer {

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(SimpleSecurityApplication.class);
//    }

    public static void main(String[] args) {
        SpringApplication.run(SimpleSecurityApplication.class, args);
    }

}
