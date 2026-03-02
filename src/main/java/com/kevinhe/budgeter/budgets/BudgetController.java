package com.kevinhe.budgeter.budgets;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
        private String name;

        public UpdateBudgetRequest() {
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Budget createBudget(@Valid @RequestBody CreateBudgetRequest request) {
        return budgetService.createBudget(request.getName());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBudgetName(@PathVariable UUID id, @Valid @RequestBody UpdateBudgetRequest request) throws IllegalStateException {
        budgetService.updateBudgetName(id, request.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudget(@PathVariable UUID id) {
        budgetService.deleteBudget(id);
    }

}
