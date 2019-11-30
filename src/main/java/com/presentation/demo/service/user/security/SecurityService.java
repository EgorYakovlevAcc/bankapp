package com.presentation.demo.service.user.security;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    void autoLogin(String username,String password);
}
