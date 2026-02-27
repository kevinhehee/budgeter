package com.kevinhe.budgeter.budgets;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    public static class CreateBudgetRequest {
        @NotBlank
        private String name;

        public CreateBudgetRequest() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UpdateBudgetRequest {
        @NotBlank
        private UUID id;

        @NotBlank
        private String newName;

        public UpdateBudgetRequest() {
        }

        public UUID getId() {
            return this.id;
        }

        public String getNewName() {
            return this.newName;
        }
    }

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Budget createBudget(@Valid @RequestBody CreateBudgetRequest request) {
        return budgetService.createBudget(request.getName());
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBudgetName(@Valid @RequestBody UpdateBudgetRequest request) {
        budgetService.updateBudgetName(request.getId(), request.getNewName());
    }

}
