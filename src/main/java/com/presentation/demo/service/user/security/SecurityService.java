package com.presentation.demo.service.user.security;

import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

public interface SecurityService {
    void autoLogin(String username, String password, Collection<? extends GrantedAuthority> authorities);
}
