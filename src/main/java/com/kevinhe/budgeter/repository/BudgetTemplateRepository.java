package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.BudgetTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetTemplateRepository extends JpaRepository<BudgetTemplate, UUID> {
    Long countBudgetTemplatesByUserId(UUID userId);
}
