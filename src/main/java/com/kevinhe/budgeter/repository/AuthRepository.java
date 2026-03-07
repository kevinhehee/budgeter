package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.AuthIdentity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<AuthIdentity, UUID> {
    Optional<AuthIdentity> findByProviderAndSub(String provider, String sub);
}
