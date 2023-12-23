package com.kredx.cashsettlement.user.api.dto;

import jakarta.validation.constraints.Size;

public class SignUpLoginRequest {

    @Size(min = 1, max = 20, message = "username should not be empty and size between 1 to 20")
    private String username;

    @Size(min = 1, max = 60, message = "Password should not be empty and size between 1 to 60")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
