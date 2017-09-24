package com.lbc.awakening.service;

import com.lbc.awakening.model.UserModel;
import com.lbc.awakening.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserModel getByEmail(String email){

        System.out.println(email);
        return userRepository.getUserByEmail(email);
    }
}
