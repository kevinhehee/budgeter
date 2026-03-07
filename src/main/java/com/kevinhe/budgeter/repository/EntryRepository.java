package com.kevinhe.budgeter.repository;

import com.kevinhe.budgeter.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    long deleteEntryById(UUID id);
    Entry getEntryById(UUID id);
    List<Entry> getEntriesByBudgetId(UUID budgetId);
}
