package com.example.SimpleCRUDAPI.config;

import com.example.SimpleCRUDAPI.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login").permitAll() // Allow authentication endpoints
                .anyRequest().authenticated() // Protect all other endpoints
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Add our custom JWT filter before the default filter
                // .build is used to build the SecurityFilterChain. By building means it will create the SecurityFilterChain instance. it's like calling the constructor of the SecurityFilterChain class. it is like recursion. it will call the constructor of the SecurityFilterChain class and return the instance of the SecurityFilterChain class which is used to build the SecurityFilterChain. 
                .build();
    }

    // if there is no @Bean annotation, the method will not be registered as a bean in the Spring application context. The method will be executed but the return value will not be registered as a bean. which means you can't autowire the return value of the method in other classes.
    @Bean
    // we need passwordEncoder bean to encode the password before saving it to the database.
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password hashing
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Provide an AuthenticationManager bean
    }
}
