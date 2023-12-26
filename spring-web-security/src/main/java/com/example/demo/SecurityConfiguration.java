package com.example.demo;

import com.example.demo.auth.JwtAuthenticationFilter;
import com.example.demo.auth.JwtAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationManager jwtAuthenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationManager jwtAuthenticationManager, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                        requests ->
                                requests.requestMatchers("/login").permitAll()
                                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(jwtAuthenticationManager)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }

}
