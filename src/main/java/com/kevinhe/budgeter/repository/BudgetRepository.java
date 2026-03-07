package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {
    List<Budget> findByUserId(UUID userId);
    Optional<Budget> findByIdAndUserId(UUID id, UUID userId);
    long deleteBudgetByIdAndUserId(UUID id, UUID userId);
}
