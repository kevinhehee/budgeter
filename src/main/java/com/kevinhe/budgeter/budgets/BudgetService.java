package com.kevinhe.budgeter.budgets;

import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.users.User;
import com.kevinhe.budgeter.users.UserRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final CurrentUserProvider currentUserProvider;

    private final UserRepository userRepository;
    private final EntryRepository entryRepository;

    public BudgetService(BudgetRepository budgetRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository, EntryRepository entryRepository) {
        this.budgetRepository = budgetRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
        this.entryRepository = entryRepository;
    }

    public List<Budget> getBudgets() {
        User user = userRepository.findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("User not in database"));

        return budgetRepository.findByUserId(user.getId());
    }

    public Budget createBudget(String name) {
        User user = userRepository
                .findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("User not in database"));

        return budgetRepository.save(new Budget(user, name));
    }

    @Transactional
    public void updateBudgetName(UUID budgetId, String newName) throws IllegalStateException {
        UUID currUserId = currentUserProvider.currentUserId();
        Budget budget = budgetRepository
                .findByIdAndUserId(budgetId, currUserId)
                .orElseThrow(() -> new IllegalStateException("Budget not in database"));

        budget.setName(newName);
    }

    @Transactional
    public void deleteBudget(UUID budgetId) {
        UUID currUserId = currentUserProvider.currentUserId();
        long deleted = budgetRepository.deleteBudgetByIdAndUserId(budgetId, currUserId);

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget not found");
        }
    }

    public List<Entry> getEntriesByBudgetId(UUID budgetId) {
        budgetRepository.findByIdAndUserId(budgetId, currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("Budget does not exist"));

        return entryRepository.getEntriesByBudgetId(budgetId);
    }
}
