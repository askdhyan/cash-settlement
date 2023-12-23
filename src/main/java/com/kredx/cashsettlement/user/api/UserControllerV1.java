package com.kredx.cashsettlement.user.api;

import com.kredx.cashsettlement.user.api.dto.LoginResponse;
import com.kredx.cashsettlement.user.api.dto.SignUpLoginRequest;
import com.kredx.cashsettlement.user.svc.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/user")
public class UserControllerV1 {
    @Autowired
    UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpLoginRequest signUpLoginRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signUp(signUpLoginRequest.getUsername(), signUpLoginRequest.getPassword()));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody SignUpLoginRequest signUpLoginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(signUpLoginRequest.getUsername(), signUpLoginRequest.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String > logout(HttpServletRequest request) {
        userService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

    @GetMapping("/security-check")
    public ResponseEntity<String> temp() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("Temp Security check endpoint, Logged User: "+ userDetails.getUsername());
    }


}
