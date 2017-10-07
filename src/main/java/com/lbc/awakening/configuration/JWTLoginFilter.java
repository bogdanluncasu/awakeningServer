package com.lbc.awakening.configuration;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbc.awakening.model.UserModel;
import com.lbc.awakening.service.SecurityService;
import com.lbc.awakening.service.TokenAuthenticationService;
import com.lbc.awakening.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;


public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    UserService userService;

    @Autowired
    SecurityService<UserModel> securityService;

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        String request = "";
        while(true){
            String tempString = req.getReader().readLine();
            if(tempString==null){
                break;
            }

            request+=tempString;
        }
        UserModel credentials;
        try {
            credentials = securityService.decrypt(request,UserModel.class);
        }catch (Exception ex){
            return null;
        }


        Authentication authentication = null;
        try {
            authentication = getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            Collections.emptyList()
                    )
            );
        }catch(InternalAuthenticationServiceException exception){
            res.setHeader("Content-type","application/json");
            res.getWriter().write(exception.getMessage());
            res.setStatus(401);
        }

        return authentication;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
        UserModel user = userService.getByEmail(auth.getName());
        tokenAuthenticationService.addAuthentication(res, auth.getName(), (User) auth.getPrincipal());

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().print("Invalid email or password.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    }
}