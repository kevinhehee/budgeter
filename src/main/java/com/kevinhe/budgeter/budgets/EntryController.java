package com.kevinhe.budgeter.budgets;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/entries")
public class EntryController {

    public static class UpdateEntryRequest {
        private String name;
        private String description;
        private Long cents;

        @PastOrPresent
        private LocalDate transactionDate;

        public String getName() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public Long getCents() {
            return this.cents;
        }

        public LocalDate getTransactionDate() {
            return transactionDate;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCents(Long cents) {
            this.cents = cents;
        }

        public void setTransactionDate(LocalDate transactionDate) {
            this.transactionDate = transactionDate;
        }
    }

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntry(@PathVariable UUID id) {
        entryService.deleteEntry(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Entry updateEntry(@PathVariable UUID id, @Valid @RequestBody UpdateEntryRequest request) {
        return entryService.updateEntry(
                id,
                request.getName(),
                request.getDescription(),
                request.getCents(),
                request.getTransactionDate()
        );
    }

}
