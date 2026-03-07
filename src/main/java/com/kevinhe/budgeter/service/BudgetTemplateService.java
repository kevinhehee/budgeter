package com.kevinhe.budgeter.service;


import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.model.BudgetTemplate;
import com.kevinhe.budgeter.model.User;
import com.kevinhe.budgeter.repository.BudgetTemplateRepository;
import com.kevinhe.budgeter.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetTemplateService {



    private final BudgetTemplateRepository budgetTemplateRepository;
    private final CurrentUserProvider currentUserProvider;
    private final UserRepository userRepository;

    public BudgetTemplateService(BudgetTemplateRepository budgetTemplateRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository) {
        this.budgetTemplateRepository = budgetTemplateRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
    }

    public BudgetTemplate createBudgetTemplate(String name) {

        if (name == null) {
            long budgetCount = budgetTemplateRepository.countBudgetTemplatesByUserId(currentUserProvider.currentUserId());
            name = "Template ".concat(String.valueOf(budgetCount + 1));
        }

        User currUser = userRepository.findById(currentUserProvider.currentUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

        return budgetTemplateRepository.save(new BudgetTemplate(currUser, name));
    }
}
