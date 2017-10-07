package com.lbc.awakening.controller;

import com.lbc.awakening.model.UserModel;
import com.lbc.awakening.service.RegisterService;
import com.lbc.awakening.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("")
public class UserController {
    @Autowired
    RegisterService registerService;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody String encrypted, HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_CREATED);
        return registerService.register(encrypted);
    }

    @GetMapping("")
    public List<UserModel> getAllUsers(){
        return this.userService.getAllUsers();
    }
}
