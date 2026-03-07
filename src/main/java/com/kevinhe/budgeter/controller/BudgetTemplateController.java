package com.kevinhe.budgeter.controller;

import com.kevinhe.budgeter.model.BudgetTemplate;
import com.kevinhe.budgeter.service.BudgetService;
import com.kevinhe.budgeter.service.BudgetTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget-templates")
public class BudgetTemplateController {

    public static class CreateBudgetTemplateRequest {
        private String name;

        public CreateBudgetTemplateRequest() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

    public static class CreateBudgetTemplateItems {

    }

    private final BudgetTemplateService budgetTemplateService;

    public BudgetTemplateController(BudgetTemplateService budgetTemplateService) {
        this.budgetTemplateService = budgetTemplateService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public BudgetTemplate createBudgetTemplate(@RequestBody CreateBudgetTemplateRequest request) {
        return budgetTemplateService.createBudgetTemplate(request.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetTemplate(@PathVariable UUID id) {
        budgetTemplateService.deleteBudgetTemplate(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BudgetTemplate> getBudgetTemplates() {
        return budgetTemplateService.getBudgetTemplates();
    }








}
