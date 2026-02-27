package com.kevinhe.budgeter.budgets;

import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.users.User;
import com.kevinhe.budgeter.users.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final CurrentUserProvider currentUserProvider;

    private final UserRepository userRepository;

    public BudgetService(BudgetRepository budgetRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget createBudget(String name) {

//        check is user even exists
        User user = userRepository.findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("User not in database"));

        return budgetRepository.save(new Budget(user, name));
    }

    public void updateBudgetName(UUID budgetId, String newName) {
        Budget budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new IllegalStateException("Budget not in database"));

        budget.setName(newName);

        budgetRepository.save(budget);
    }
}
