package com.kevinhe.budgeter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "budget_templates")
public class BudgetTemplate {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
        if (updatedAt == null) {
            updatedAt = Instant.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }

    public BudgetTemplate() {

    }

    public BudgetTemplate(User user, String name) {
        this.name = name;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }
}
