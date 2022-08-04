package com.sparta.springcore.controller;

import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/login")
    public String login() {
        return "login";
    }



    @PostMapping("/user/signup")
    public String registerUser(@RequestBody SignupRequestDto requestDto) {
        userService.registerUser(requestDto);
        return "hi";
    }
}