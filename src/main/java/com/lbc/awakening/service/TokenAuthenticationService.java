package com.lbc.awakening.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbc.awakening.model.UserModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;

@Service
public class TokenAuthenticationService {

    private static long EXPIRATIONTIME;
    private static String SECRET;
    private static String TOKEN_PREFIX;
    private static String HEADER_STRING;

    @Autowired
    UserService userService;

    @Value("${awakening.security.expirationtime}")
    public void setEXPIRATIONTIME(long EXPIRATIONTIME) {
        TokenAuthenticationService.EXPIRATIONTIME = EXPIRATIONTIME;
    }

    @Value("${awakening.security.secret}")
    public void setSECRET(String SECRET) {
        TokenAuthenticationService.SECRET = SECRET;
    }

    @Value("${awakening.security.token_prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }

    @Value("${awakening.security.header}")
    public void setHeaderString(String headerString) {
        HEADER_STRING = headerString;
    }

    @Autowired
    SecurityService<UserModel> userModelSecurityService;

    public void addAuthentication(HttpServletResponse res, String email, User user) throws IOException {
        String JWT = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String encryptedToken = this.userModelSecurityService.encrypt(JWT);
        res.addHeader("Access-Control-Allow-Headers", HEADER_STRING);
        res.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
        res.addHeader(HEADER_STRING, encryptedToken);
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(user));

    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token;
        try {
            token = this.userModelSecurityService.decryptToString(request.getHeader(HEADER_STRING));
        } catch (Exception ex) {
            return null;
        }
        System.out.println(token);
        if (token != null) {
            String emailAddress = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            UserModel user = userService.getByEmail(emailAddress);
            if ("admin@lunkasu.com".equals(emailAddress)) {
                return new UsernamePasswordAuthenticationToken(emailAddress, null,
                        Arrays.asList(new SimpleGrantedAuthority("ADMIN")));
            }
            return emailAddress != null && user != null ?
                    new UsernamePasswordAuthenticationToken(emailAddress, null,
                            Arrays.asList(new SimpleGrantedAuthority("USER"))) :
                    null;
        }
        return null;
    }
}
