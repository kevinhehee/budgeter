package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.BudgetTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetTemplateRepository extends JpaRepository<BudgetTemplate, UUID> {
    Long deleteBudgetTemplateByIdAndUserId(UUID id, UUID userId);
    List<BudgetTemplate> getBudgetTemplatesByUserId(UUID userId);

    Long countByUserId(UUID userId);
    Boolean existsBudgetTemplateByNameAndUserId(String name, UUID userId);
}
