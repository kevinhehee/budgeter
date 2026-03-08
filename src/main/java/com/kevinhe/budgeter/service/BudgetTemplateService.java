package com.kevinhe.budgeter.service;


import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.model.BudgetTemplate;
import com.kevinhe.budgeter.model.User;
import com.kevinhe.budgeter.repository.BudgetTemplateRepository;
import com.kevinhe.budgeter.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    private String generateDefaultTemplateName(UUID userId) {
        int templateNum = 1;

        while (budgetTemplateRepository.existsBudgetTemplateByNameAndUserId("Template " + templateNum, userId)) {
            templateNum++;
        }
        return "Template " + templateNum;
    }

    private static final int MAX_BUDGET_TEMPLATES_PER_USER = 3;

    public BudgetTemplate createBudgetTemplate(String name) {

        User currUser = userRepository.findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (budgetTemplateRepository.countByUserId(currUser.getId()) >= MAX_BUDGET_TEMPLATES_PER_USER) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Template limit reached");
        }

        if (name == null || name.isBlank()) {
            name = generateDefaultTemplateName(currUser.getId());
        }

        return budgetTemplateRepository.save(new BudgetTemplate(currUser, name));
    }

    @Transactional
    public void deleteBudgetTemplates() {
        long deleted = budgetTemplateRepository.deleteBudgetTemplatesByUserId(currentUserProvider.currentUserId());

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Budget Templates Found");
        }
    }

    @Transactional
    public void deleteBudgetTemplate(UUID id) {
        long deleted = budgetTemplateRepository.deleteBudgetTemplateByIdAndUserId(id, currentUserProvider.currentUserId());

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Budget Template not found");
        }
    }

    public List<BudgetTemplate> getBudgetTemplates() {
        User user = userRepository.findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new IllegalStateException("User not in database"));

        return budgetTemplateRepository.getBudgetTemplatesByUserId(user.getId());
    }
}
