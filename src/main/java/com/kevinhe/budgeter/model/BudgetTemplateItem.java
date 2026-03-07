package com.kevinhe.budgeter.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "budget_template_items")
public class BudgetTemplateItem {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "budget_template_id", nullable = false)
    private BudgetTemplate budgetTemplate;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "cents", nullable = false)
    private Long cents;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

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

    public BudgetTemplateItem() {}

    public BudgetTemplateItem(BudgetTemplate budgetTemplate, String label, Long cents, String type, Integer displayOrder) {
        this.budgetTemplate = budgetTemplate;
        this.label = label;
        this.cents = cents;
        this.type = type;
        this.displayOrder = displayOrder;
    }

}
