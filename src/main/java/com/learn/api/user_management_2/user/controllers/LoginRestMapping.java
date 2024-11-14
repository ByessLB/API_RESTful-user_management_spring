package com.learn.api.user_management_2.user.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.api.user_management_2.user.dtos.UserDTO;
import com.learn.api.user_management_2.user.dtos.requests.LoginRequestDTO;
import com.learn.api.user_management_2.user.entities.User;
import com.learn.api.user_management_2.user.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/login")
@Slf4j
public class LoginRestMapping {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        User user = userService.login(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
        return ResponseEntity.ok(new UserDTO(user));
    }
}
