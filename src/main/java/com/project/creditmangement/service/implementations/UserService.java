package com.project.creditmangement.service.implementations;
import com.project.creditmangement.exception.CustomAuthenticationException;
import com.project.creditmangement.model.Role;
import com.project.creditmangement.model.User;
import com.project.creditmangement.model.dto.UserLoginDTO;
import com.project.creditmangement.repository.RoleRepository;
import com.project.creditmangement.security.TokenProvider;
import com.project.creditmangement.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;



    @PostConstruct
    private void postConstruct() {
        //Basically overriding the existing user.
        User newAdmin =userRepository.findByUsername("zeynel") ;
        newAdmin.setPassword("zeynel");
        newAdmin.setEmail("admin@admin.com");
        newAdmin.setRoles(Collections.singletonList(roleRepository.getById(1)));
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        userRepository.save(newAdmin);
    }




    public String signin(String username, String password) {
        if (userRepository.findByUsername(username) != null) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            List<Role> roles = userRepository.findByUsername(username).getRoles();
            return tokenProvider.createToken(username, roles);
        } else {
            throw new CustomAuthenticationException("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
        }
    }

    public String signup(User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Collections.singletonList(roleRepository.getById(2)));
            userRepository.save(user);
            return tokenProvider.createToken(user.getUsername(), user.getRoles());
        } else {
            throw new CustomAuthenticationException("User with this username already exists, please try another username", HttpStatus.BAD_REQUEST);
        }
    }

    public void delete(String username) {
        if (userRepository.existsByUsername(username) != false) {
            userRepository.deleteByUsername(username);
        } else {
            throw new CustomAuthenticationException("Username is not found", HttpStatus.NOT_FOUND);
        }
    }

    public User search(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomAuthenticationException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }


    public String refresh(String username) {
        return tokenProvider.createToken(username, userRepository.findByUsername(username).getRoles());
    }

}
