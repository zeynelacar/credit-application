package com.project.creditmangement.security;

import com.project.creditmangement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByUsername(String username);

    boolean existsByPassword(String password);

    User findByUsername(String username);

    void deleteByUsername(String username);




}
