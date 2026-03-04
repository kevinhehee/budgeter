package com.kevinhe.budgeter.budgets;


import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.users.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class EntryService {

    private final BudgetRepository budgetRepository;

    private final CurrentUserProvider currentUserProvider;

    private final UserRepository userRepository;
    private final EntryRepository entryRepository;

    public EntryService(BudgetRepository budgetRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository, EntryRepository entryRepository) {
        this.budgetRepository = budgetRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
    }

    public Entry createEntry(
            UUID budgetId,
            String name,
            String description,
            LocalDate transactionDate,
            long cents
    ) {

        Budget budget = budgetRepository
                .findByIdAndUserId(budgetId, currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("Budget not in database"));

        return entryRepository.save(new Entry(budget, name, description, transactionDate, cents));
    }

    public void deleteEntry(UUID id) {
        long deleted = entryRepository.deleteEntryById(id);

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    public Entry updateEntry(
            UUID id,
            String name,
            String description,
            Long cents,
            LocalDate transactionDate
    ) {


        Entry entry = entryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("Entry not in database"));

        if (!currentUserProvider.currentUserId().equals(entry.getBudget().getUser().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (name != null) {
            if (name.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name cannot be blank");
            }
            entry.setName(name);
        }
        if (description != null) {
            entry.setDescription(description);
        }
        if (cents != null) {
            entry.setCents(cents);
        }
        if (transactionDate != null) {
            entry.setTransactionDate(
                    transactionDate.atStartOfDay().toInstant(ZoneOffset.UTC)
            );
        }
        return entry;
    }
}
