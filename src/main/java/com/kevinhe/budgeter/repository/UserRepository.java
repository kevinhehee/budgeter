package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
