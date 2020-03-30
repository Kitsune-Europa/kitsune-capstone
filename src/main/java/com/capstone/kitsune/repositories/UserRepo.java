package com.capstone.kitsune.repositories;

import com.capstone.kitsune.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}