package com.presentation.demo.service.user.logdetails;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.AuthenticatedUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class UserLogoutSuccessHandlerServiceImpl implements UserLogoutSuccessHandlerService {

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        if (session != null){
            User user = authenticatedUserRepository.findAuthenticatedUserByUsername(authentication.getName());
            authenticatedUserRepository.delete(user);
            session.removeAttribute("user");
        }

    }
}
