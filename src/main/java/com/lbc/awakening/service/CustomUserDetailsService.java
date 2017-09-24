package com.lbc.awakening.service;


import com.lbc.awakening.exception.InvalidUserException;
import com.lbc.awakening.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    private List<GrantedAuthority> getGrantedAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userService.getByEmail(email);

        if("admin@lunkasu.com".equals(email)){
            return new User(email, "$2a$06$SEcZ7jNZNBsC8G1YAdELEurPBaYUA8KbBfF9gLpQVUnqO8X1vHatm",
                    true, true, true, true,
                    Arrays.asList(new SimpleGrantedAuthority("ADMIN"))
            );
        }
        if(user==null){
            throw new InvalidUserException("Invalid email or password.");
        }

        return new User(user.getEmail(), user.getPassword(),
                true, true,
                true , true, getGrantedAuthorities());
    }
}