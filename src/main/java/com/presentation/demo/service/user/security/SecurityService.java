package com.presentation.demo.service.user.security;

import com.presentation.demo.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface SecurityService {
    void autoLogin(String username, String password, Collection<? extends GrantedAuthority> authorities);
    void resetUserPassword(User user, String temporaryPassword);
}
