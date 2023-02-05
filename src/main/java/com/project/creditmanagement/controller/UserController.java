package com.project.creditmanagement.controller;

import com.project.creditmanagement.model.entity.User;
import com.project.creditmanagement.model.dto.UserLoginDTO;
import com.project.creditmanagement.model.mapper.UserMapper;
import com.project.creditmanagement.service.UserService;
import com.project.creditmanagement.service.implementations.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    @PostMapping("/signin")
    public String login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.signin(userLoginDTO.getUsername(),userLoginDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserLoginDTO user) {
        UserMapper userMapper = new UserMapper();
        return userService.signup(userMapper.map(user, User.class));
    }

    @DeleteMapping(value = "/delete/{username}")
    public String delete(@PathVariable String username) {
        userService.delete(username);
        return username;
    }

    @GetMapping("/get-by-username")
    public User search(@RequestParam String username){
        return userService.search(username);
    }

}