package com.realthomasmiles.marketplace.security.form;

import com.realthomasmiles.marketplace.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FormBasedJWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;


    public FormBasedJWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && password != null) {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            password,
                            new ArrayList<>()
                    )
            );
        }

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        if (authResult.getPrincipal() != null) {
            User user = (User) authResult.getPrincipal();
            String login = user.getUsername();
            if (login != null && login.length() > 0) {
                Claims claims = Jwts.claims().setSubject(login);
                List<String> roles = new ArrayList<>();
                user.getAuthorities()
                        .forEach(grantedAuthority -> roles.add(grantedAuthority.getAuthority()));

                claims.put("roles", roles);
                String token = Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET.getBytes())
                        .compact();
                response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
            }
        }
    }
}
