package com.kevinhe.budgeter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "cents", nullable = false)
    private long cents;

    @Column(name = "transaction_date", nullable = true)
    private Instant transactionDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Entry() {}

    public Entry(Budget budget, String name, String description, LocalDate transactionDate, long cents) {
        this.budget = budget;
        this.name = name;
        this.description = description;
        this.cents = cents;
        this.transactionDate = transactionDate.atStartOfDay(ZoneOffset.UTC).toInstant();
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            this.id = UUID.randomUUID();
        }
        if (createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public Budget getBudget() { return budget; }
    public long getCents() { return cents; }
    public Instant getTransactionDate() { return transactionDate; }
    public Instant getCreatedAt() { return createdAt; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCents(long cents) { this.cents = cents; }
    public void setTransactionDate(Instant transactionDate) { this.transactionDate = transactionDate; }

    

}
