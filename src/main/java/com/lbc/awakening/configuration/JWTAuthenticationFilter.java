package com.lbc.awakening.configuration;

import com.lbc.awakening.service.TokenAuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {


    @Autowired
    TokenAuthenticationService tokenAuthenticationService;
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain)
            throws IOException, ServletException {

        Authentication authentication = null;
        try{
            authentication = tokenAuthenticationService
                    .getAuthentication((HttpServletRequest)request);
        }catch (ExpiredJwtException ex){
            HttpServletResponse httpServletResponse = ((HttpServletResponse)response);
            httpServletResponse.setStatus(401);
            httpServletResponse.setHeader("Authorization","expired");
            return;

        }

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request,response);
    }
}