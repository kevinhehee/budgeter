package com.kevinhe.budgeter.controller;

import com.kevinhe.budgeter.model.BudgetTemplate;
import com.kevinhe.budgeter.model.BudgetTemplateItem;
import com.kevinhe.budgeter.service.BudgetTemplateItemService;
import com.kevinhe.budgeter.service.BudgetTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/budget-templates")
public class BudgetTemplateController {

    private final BudgetTemplateItemService budgetTemplateItemService;

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

    public static class CreateBudgetTemplateItemsRequest {
        private String label;
        private Long cents;
        private String type;
        private Integer displayOrder;

        public CreateBudgetTemplateItemsRequest(
                String label,
                Long cents,
                String type,
                Integer displayOrder
        ) {
            this.label = label;
            this.cents = cents;
            this.type = type;
            this.displayOrder = displayOrder;
        }

        String getLabel() {
            return this.label;
        }

        Long getCents() {
            return this.cents;
        }

        String getType() {
            return this.type;
        }

        Integer getDisplayOrder() {
            return this.displayOrder;
        }
    }

    private final BudgetTemplateService budgetTemplateService;

    public BudgetTemplateController(BudgetTemplateService budgetTemplateService, BudgetTemplateItemService budgetTemplateItemService) {
        this.budgetTemplateService = budgetTemplateService;
        this.budgetTemplateItemService = budgetTemplateItemService;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetTemplate getBudgetTemplate(@PathVariable UUID id) {
        return budgetTemplateService.getBudgetTemplate(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BudgetTemplate> getBudgetTemplates() {
        return budgetTemplateService.getBudgetTemplates();
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

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetTemplates() {
        budgetTemplateService.deleteBudgetTemplates();
    }



    // budget template items

    @GetMapping("/budget-template-items/{budgetTemplateItemId}")
    @ResponseStatus(HttpStatus.OK)
    public BudgetTemplateItem getBudgetTemplateItem(@PathVariable UUID budgetTemplateItemId) {
        return budgetTemplateItemService.getBudgetTemplateItemById(budgetTemplateItemId);
    }

    @GetMapping("/{budgetTemplateId}/budget-template-items")
    @ResponseStatus(HttpStatus.OK)
    public List<BudgetTemplateItem> getBudgetTemplateItems(@PathVariable UUID budgetTemplateId) {
        return budgetTemplateItemService.getBudgetTemplateItemsByBudgetTemplateId(budgetTemplateId);
    }

    @PostMapping("/{id}/budget-template-items")
    @ResponseStatus(HttpStatus.OK)
    public BudgetTemplateItem createBudgetTemplateItem(@PathVariable UUID id, @RequestBody CreateBudgetTemplateItemsRequest request) {
        return budgetTemplateItemService.createBudgetTemplateItem(
                id,
                request.getLabel(),
                request.getCents(),
                request.getType(),
                request.getDisplayOrder()
        );
    }

    @DeleteMapping("/{id}/budget-template-items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBudgetTemplateItem(@PathVariable UUID id) {
        budgetTemplateItemService.deleteBudgetTemplateItem(id);
    }





}
