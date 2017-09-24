package com.lbc.awakening.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbc.awakening.exception.InvalidUserException;
import com.lbc.awakening.model.UserModel;
import com.lbc.awakening.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class RegisterService {
    private SecurityService<UserModel> securityService;
    private UserRepository userModelRepository;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public RegisterService(SecurityService<UserModel> securityService,
                           UserRepository userModelRepository){
        this.userModelRepository = userModelRepository;
        this.securityService = securityService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private String encodePassword(String password){
        return this.passwordEncoder.encode(password);
    }
    public String register(String encrypted){
        UserModel userModel = securityService.decrypt(encrypted,UserModel.class);
        if(userModel.isValid()){
            userModel.setPassword(encodePassword(userModel.getPassword()));
            try {
                UserModel savedModel = this.userModelRepository.save(userModel);

                return securityService.encrypt(new ObjectMapper().writeValueAsString(savedModel));
            }catch (Exception exc){
                if(exc instanceof DataIntegrityViolationException){
                    throw new InvalidUserException("Email already in use.");
                }
            }
        }

        throw new InvalidUserException("User fields are not completed correctly");
    }
}
