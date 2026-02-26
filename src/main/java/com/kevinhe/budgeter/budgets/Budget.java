package com.kevinhe.budgeter.budgets;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kevinhe.budgeter.users.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    protected Budget() {}

    public Budget(User user, String name) {
        this.name = name;
        this.user = user;
    }

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (createdAt == null) {
            createdAt = Instant.now();
        }
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