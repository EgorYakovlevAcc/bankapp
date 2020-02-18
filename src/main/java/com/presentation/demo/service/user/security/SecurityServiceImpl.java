package com.presentation.demo.service.user.security;

import com.presentation.demo.model.User;
import com.presentation.demo.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Transactional
@Service
public class SecurityServiceImpl implements SecurityService{

    private Logger AUTH_LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Override
    public void autoLogin(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        try {
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,password,authorities);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            AUTH_LOGGER.info("Login failed.");
        }
    }

    @Override
    public void resetUserPassword(User user, String temporaryPassword) {
        User targetUser = userService.findUserById(user.getId());
        if (targetUser != null){
            targetUser.setPassword(temporaryPassword);
            targetUser.setResetPasswordToken(null);
            userService.save(targetUser);
        }
    }
}
