//package com.example.demo.auth;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.CredentialsExpiredException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class JwtAuthenticationManager implements AuthenticationManager {
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationManager.class);
//
//    private final JwtTokenUtil jwtUtil;
//
//    public JwtAuthenticationManager(JwtTokenUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String authToken = authentication.getCredentials().toString();
//        log.debug("auth request received {}", authentication.getName());
//        if (!jwtUtil.validateToken(authToken)) {
//            throw new CredentialsExpiredException("Invalid token");
//        }
//        String user = jwtUtil.getUsernameFromToken(authToken);
//        List<String> roles = jwtUtil.getRoleList(authToken);
//        return new UsernamePasswordAuthenticationToken(
//                user, null,
//                roles.stream().map(SimpleGrantedAuthority::new).toList()
//        );
//    }
//}
