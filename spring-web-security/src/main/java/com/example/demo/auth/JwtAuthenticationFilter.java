package com.example.demo.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.nonNull;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenUtil jwtUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Authorization: Bearer <token>
        String authValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (nonNull(authValue) && authValue.startsWith(BEARER_PREFIX)) {
            String authToken = authValue.substring(BEARER_PREFIX.length());
            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            Authentication authentication = authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // continue with the filter chain
        filterChain.doFilter(request, response);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authToken = authentication.getCredentials().toString();
        if (!jwtUtil.validateToken(authToken)) {
            throw new CredentialsExpiredException("Invalid token");
        }
        String user = jwtUtil.getUsernameFromToken(authToken);
        List<String> roles = jwtUtil.getRoleList(authToken);
        return new UsernamePasswordAuthenticationToken(
                user, null,
                roles.stream().map(SimpleGrantedAuthority::new).toList()
        );
    }
}
