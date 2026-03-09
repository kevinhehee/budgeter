package com.kevinhe.budgeter.controller;

import com.kevinhe.budgeter.model.Budget;
import com.kevinhe.budgeter.model.Entry;
import com.kevinhe.budgeter.service.BudgetService;
import com.kevinhe.budgeter.service.EntryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
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

    public static class CreateEntryRequest {
        @NotBlank
        private String name;

        private String description;

        @NotNull
        private long cents;

        LocalDate transactionDate;

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public LocalDate getTransactionDate() {
            return this.transactionDate;
        }

        public long getCents() {
            return this.cents;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
        }

        public void setCents(long cents) {
            this.cents = cents;
        }
    }

    public record EntryResponse(
            UUID id,
            String name,
            String description,
            long cents,
            LocalDate transactionDate
    ) {}

    private final BudgetService budgetService;

    private final EntryService entryService;

    public BudgetController(EntryService entryService, BudgetService budgetService) {
        this.entryService = entryService;
        this.budgetService = budgetService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Budget> getBudgets() {
        return budgetService.getBudgets();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Budget getBudget(@PathVariable UUID id) {
        return budgetService.getBudget(id);
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

    @GetMapping("/{id}/entries")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Entry> getEntriesByBudgetId(@PathVariable UUID id) {
        return budgetService.getEntriesByBudgetId(id);
    }

    @PostMapping("/{id}/entries")
    @ResponseStatus(HttpStatus.CREATED)
    public EntryResponse createEntry(@PathVariable UUID id, @Valid @RequestBody CreateEntryRequest request) {
        Entry entry = entryService.createEntry(
            id,
            request.getName(),
            request.getDescription(),
            request.getTransactionDate(),
            request.getCents()
        );

        return new EntryResponse(
                entry.getId(),
                entry.getName(),
                entry.getDescription(),
                entry.getCents(),
                entry.getTransactionDate().atZone(ZoneOffset.UTC).toLocalDate()
        );
    }
}
