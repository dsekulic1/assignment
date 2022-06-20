package com.example.helloWorld.repository;

import com.example.helloWorld.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);
}
