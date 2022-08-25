package com.project.creditmangement.controller;

import com.project.creditmangement.model.User;
import com.project.creditmangement.model.dto.UserLoginDTO;
import com.project.creditmangement.service.implementations.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {


    private UserService userService;

    @PostMapping("/signin")
    public String login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.signin(userLoginDTO.getUsername(),userLoginDTO.getPassword());
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        return userService.signup(user);
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