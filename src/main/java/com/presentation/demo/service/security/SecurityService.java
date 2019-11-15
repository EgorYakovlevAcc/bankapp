package com.presentation.demo.service.security;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    void autologin(String username, String password);
}
