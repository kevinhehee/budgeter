package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.BudgetTemplateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface BudgetTemplateItemRepository extends JpaRepository<BudgetTemplateItem, UUID> {
    long deleteBudgetTemplateItemById(UUID id);

    List<BudgetTemplateItem> getBudgetTemplateItemsBy(UUID id);
}
