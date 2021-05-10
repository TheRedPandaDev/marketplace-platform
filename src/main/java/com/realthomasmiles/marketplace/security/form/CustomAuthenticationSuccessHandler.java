package com.realthomasmiles.marketplace.security.form;

import com.realthomasmiles.marketplace.model.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (UserRole.ADMIN.toString().equals(authority.getAuthority())) {
                response.sendRedirect("/dashboard");
            } else if (UserRole.USER.toString().equals(authority.getAuthority())) {
                response.sendRedirect("/home");
            }
        }
    }

}
