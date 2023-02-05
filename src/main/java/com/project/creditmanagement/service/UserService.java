package com.project.creditmanagement.service;

import com.project.creditmanagement.model.entity.User;

public interface UserService {

    String signin(String username, String password);

    String signup(User user);

    void delete(String username);

    User search(String username);
    String refresh(String username);

}
