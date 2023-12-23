package com.kredx.cashsettlement.user.svc.impl;

import com.kredx.cashsettlement.config.JwtService;
import com.kredx.cashsettlement.config.TokenBlacklist;
import com.kredx.cashsettlement.user.api.dto.LoginResponse;
import com.kredx.cashsettlement.user.svc.UserService;
import com.kredx.cashsettlement.user.svc.impl.dao.UserRepository;
import com.kredx.cashsettlement.user.svc.impl.dao.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import com.kredx.cashsettlement.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    TokenBlacklist tokenBlacklist;

    @Override
    public User signUp(String username, String password) throws BadRequestException{
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BadRequestException("Username already exists. Please try some other username");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(SecurityConfig.passwordEncoder().encode(password)); // Ensure to encode the password
        userRepository.save(user);
        return user;
    }

    @Override
    public LoginResponse login(String username, String password) {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if(! authentication.isAuthenticated()){
            throw new BadRequestException("Invalid Credentials, Please enter correct username and password.");
        }

        // Set the authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponse().setMessage(String.format("Login successful! Welcome, %s", username))
                .setAccessToken(jwtService.generateToken(username));
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        if(token != null){
            tokenBlacklist.addToBlacklist(token);
        }
        SecurityContextHolder.clearContext(); // Session Clear
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        // Get the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        // Check if the Authorization header is not null and starts with "Bearer "
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            return authorizationHeader.substring(7);
        }

        // If the Authorization header is not valid, return null
        return null;
    }
}
