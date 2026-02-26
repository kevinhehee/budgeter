CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
   id UUID PRIMARY KEY,
   email TEXT UNIQUE,
   display_name TEXT,
   created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

INSERT INTO users (id, email, display_name)
VALUES ('00000000-0000-0000-0000-000000000001', 'local@local', 'Local User')
ON CONFLICT DO NOTHING;


CREATE TABLE budgets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL,
    name TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_budgets_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_budgets_user_id ON budgets(user_id);