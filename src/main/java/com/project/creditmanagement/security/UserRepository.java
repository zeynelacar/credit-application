package com.project.creditmanagement.security;

import com.project.creditmanagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);

    boolean existsByPassword(String password);

    User findByUsername(String username);

    void deleteByUsername(String username);




}
