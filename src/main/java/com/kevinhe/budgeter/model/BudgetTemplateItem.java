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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BudgetTemplate getBudgetTemplate() {
        return budgetTemplate;
    }

    public void setBudgetTemplate(BudgetTemplate budgetTemplate) {
        this.budgetTemplate = budgetTemplate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getCents() {
        return cents;
    }

    public void setCents(Long cents) {
        this.cents = cents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
