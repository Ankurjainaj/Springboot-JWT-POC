package com.example.jwt.simpleSecurity.config;

import com.example.jwt.simpleSecurity.exception.CustomAccessDeniedHandler;
import com.example.jwt.simpleSecurity.exception.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class ServerSecurityConfig {

//    @Autowired
//    private CustomDetailsService customDetailsService;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    public ServerSecurityConfig(CustomAuthenticationEntryPoint customAuthenticationEntryPoint, @Qualifier("userService")
    UserDetailsService userDetailsService) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    @Autowired
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customDetailsService).passwordEncoder(encoder());
//    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authz) -> {
                            // dont authenticate this particular request
                            // make sure we use stateless session; session won't be used to
                            // store user's state.
                            try {
                                authz
                                        .antMatchers("/api/signin/**").permitAll()
                                        .antMatchers("/api/glee/**").hasAnyAuthority("ADMIN", "USER")
                                        .antMatchers("/api/users/**").hasAuthority("ADMIN")
                                        .antMatchers("/api/**").authenticated()
                                        .anyRequest().authenticated()
                                        .and()
                                        .sessionManagement()
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                        .and()
                                        .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
                                        .accessDeniedHandler(new CustomAccessDeniedHandler());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                                throw new RuntimeException(e);
                            }
                        }
                )
                // Add a filter to validate the tokens with every request
                .httpBasic(withDefaults());
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return WebSecurity::ignoring;
//    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Autowired
//    private UserDetailsService jwtUserDetailsService;
//
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests((authz) -> {
//                            try {
//                                // dont authenticate this particular request
//                                authz.antMatchers("/authenticate").permitAll()
//                                        // all other requests need to be authenticated
//                                        .anyRequest().authenticated().and()
//                                        // make sure we use stateless session; session won't be used to
//                                        // store user's state.
//                                        .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        }
//                )
//                // Add a filter to validate the tokens with every request
//                .httpBasic(withDefaults()).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        // configure AuthenticationManager so that it knows from where to load
////        // user for matching credentials
////        // Use BCryptPasswordEncoder
////        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
////    }
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        // We don't need CSRF for this example
//        httpSecurity.csrf().disable()
//                // dont authenticate this particular request
//                .authorizeRequests().antMatchers("/authenticate").permitAll().
//                // all other requests need to be authenticated
//                        anyRequest().authenticated().and().
//                // make sure we use stateless session; session won't be used to
//                // store user's state.
//                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        // Add a filter to validate the tokens with every request
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}
