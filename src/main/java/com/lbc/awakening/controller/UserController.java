package com.lbc.awakening.controller;

import com.lbc.awakening.model.UserModel;
import com.lbc.awakening.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    RegisterService registerService;

    @PostMapping("/register")
    public String register(@RequestBody String encrypted, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_CREATED);
        return registerService.register(encrypted);
    }
}
