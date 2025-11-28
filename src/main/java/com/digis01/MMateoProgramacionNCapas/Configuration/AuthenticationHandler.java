package com.digis01.MMateoProgramacionNCapas.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class AuthenticationHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String redirect = request.getContextPath();
        
        if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            redirect= "/UsuarioIndex";
        } else if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))){
            redirect = "/usuario";
        }else if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STUDENT"))){
            redirect = "/usuario";
        }else if(authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"))){
            redirect = "/usuario";
        }
        response.sendRedirect(redirect);
    }
    
}
