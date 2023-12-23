package com.kredx.cashsettlement.user.svc;

import com.kredx.cashsettlement.user.api.dto.LoginResponse;
import com.kredx.cashsettlement.user.svc.impl.dao.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    User signUp(String username, String password);

    LoginResponse login(String username, String password);

    void logout(HttpServletRequest request);
}
