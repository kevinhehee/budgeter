
CREATE TABLE budget_templates (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    name TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_budget_template_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    CONSTRAINT uq_budget_templates_user_name
        UNIQUE (user_id, name)
);

CREATE INDEX IF NOT EXISTS idx_budget_templates_user_id
    ON budget_templates(user_id);

CREATE TABLE budget_template_items (
    id UUID PRIMARY KEY,
    budget_template_id UUID NOT NULL,
    label TEXT NOT NULL,
    amount_cents BIGINT NOT NULL,
    type TEXT NOT NULL,
    display_order INT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_budget_template_item
        FOREIGN KEY (budget_template_id)
        REFERENCES budget_templates(id)
        ON DELETE CASCADE,
    CONSTRAINT chk_budget_template_items_amount_nonnegative
        CHECK (amount_cents >= 0),
    CONSTRAINT chk_budget_template_items_type
        CHECK (type IN ('INCOME', 'EXPENSE')),
    CONSTRAINT chk_budget_template_items_display_order
        CHECK (display_order >= 0),
    CONSTRAINT uq_budget_template_items_template_display_order
        UNIQUE (budget_template_id, display_order)

);

CREATE INDEX IF NOT EXISTS idx_budget_template_items_budget_template_id
    ON budget_template_items(budget_template_id);