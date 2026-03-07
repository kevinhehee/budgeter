package com.kevinhe.budgeter.users;

import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.budgets.Budget;
import com.kevinhe.budgeter.budgets.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final BudgetRepository budgetRepository;
    private final CurrentUserProvider currentUserProvider;
    private final UserRepository userRepository;

    public UserService(BudgetRepository budgetRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
    }

}
