package com.example.demo.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.nonNull;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtAuthenticationManager jwtAuthenticationManager;

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager) {
        this.jwtAuthenticationManager = jwtAuthenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization: Bearer <token>
        String authValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (nonNull(authValue) && authValue.startsWith(BEARER_PREFIX)) {
            String authToken = authValue.substring(BEARER_PREFIX.length());
            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            Authentication authentication = this.jwtAuthenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
