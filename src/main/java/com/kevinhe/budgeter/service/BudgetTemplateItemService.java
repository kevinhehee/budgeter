package com.kevinhe.budgeter.service;

import com.kevinhe.budgeter.auth.CurrentUserProvider;
import com.kevinhe.budgeter.model.BudgetTemplate;
import com.kevinhe.budgeter.model.BudgetTemplateItem;
import com.kevinhe.budgeter.model.User;
import com.kevinhe.budgeter.repository.BudgetTemplateItemRepository;
import com.kevinhe.budgeter.repository.BudgetTemplateRepository;
import com.kevinhe.budgeter.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class BudgetTemplateItemService {


    private final BudgetTemplateRepository budgetTemplateRepository;
    private final BudgetTemplateItemRepository budgetTemplateItemRepository;
    private final CurrentUserProvider currentUserProvider;
    private final UserRepository userRepository;

    public BudgetTemplateItemService(BudgetTemplateRepository budgetTemplateRepository, BudgetTemplateItemRepository budgetTemplateItemRepository, CurrentUserProvider currentUserProvider, UserRepository userRepository) {
        this.budgetTemplateRepository = budgetTemplateRepository;
        this.budgetTemplateItemRepository = budgetTemplateItemRepository;
        this.currentUserProvider = currentUserProvider;
        this.userRepository = userRepository;
    }



    public BudgetTemplateItem createBudgetTemplateItem(
            UUID budgetId,
            String label,
            Long cents,
            String type,
            Integer displayOrder
    ) {
        BudgetTemplate budgetTemplate = budgetTemplateRepository.findByIdAndUserId(budgetId, currentUserProvider.currentUserId())
                .orElseThrow(() -> new RuntimeException("Budget template does not exist"));

        return budgetTemplateItemRepository.save(new BudgetTemplateItem(budgetTemplate, label, cents, type, displayOrder));
    }

    @Transactional
    public void deleteBudgetTemplateItem(UUID id) {
        long deleted = budgetTemplateItemRepository.deleteBudgetTemplateItemById(id);

        if (deleted == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public List<BudgetTemplateItem> getBudgetTemplateItemsByBudgetTemplateId(UUID id) {

        User currUser = userRepository.findById(currentUserProvider.currentUserId())
                .orElseThrow(() -> new RuntimeException("User doesn't exist"));

        UUID currUserId = currUser.getId();
        UUID budgetOwnerUserId = budgetTemplateRepository.getBudgetTemplateById(id).getUser().getId();
        if (!currUserId.equals(budgetOwnerUserId)) {
            throw new RuntimeException("Budget Template doesn't exist");
        }



        return budgetTemplateItemRepository.getBudgetTemplateItemsBy(id);
    }

}
