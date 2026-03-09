package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    long deleteEntryById(UUID id);
    Optional<Entry> getEntryByIdAndBudgetUserId(UUID id, UUID userId);
    List<Entry> getEntriesByBudgetId(UUID budgetId);
}
