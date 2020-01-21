package com.presentation.demo.service.user.logdetails;

import com.presentation.demo.model.User;
import com.presentation.demo.repository.AuthenticatedUserRepository;
import com.presentation.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class UserAuthenticatedSuccessHandlerServiceImpl implements UserAuthenticatedSuccessHandlerService{

    @Autowired
    private AuthenticatedUserRepository authenticatedUserRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        HttpSession session = httpServletRequest.getSession();
        if (session != null){
            User user = userRepository.findUserByUsername(authentication.getName());
            if (authenticatedUserRepository.findAuthenticatedUserById(user.getId()) == null){
                authenticatedUserRepository.save(user);
                session.setAttribute("user",user);
            }
        }
    }
}
