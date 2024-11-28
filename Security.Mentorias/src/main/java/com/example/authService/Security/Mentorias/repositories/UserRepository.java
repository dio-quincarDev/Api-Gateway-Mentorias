package com.example.authService.Security.Mentorias.repositories;

import com.example.authService.Security.Mentorias.common.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
Optional<UserModel> findByEmail(String email);
}
