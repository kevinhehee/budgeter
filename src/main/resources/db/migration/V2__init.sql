ALTER TABLE auth_identity
ADD COLUMN provider TEXT;

UPDATE auth_identity
SET provider = 'google'
WHERE provider IS NULL;

UPDATE auth_identity
SET provider = 'local'
WHERE user_id = '00000000-0000-0000-0000-000000000001';

ALTER TABLE auth_identity
ALTER COLUMN provider SET NOT NULL;

ALTER TABLE auth_identity
DROP CONSTRAINT IF EXISTS auth_identity_sub_key;

CREATE UNIQUE INDEX IF NOT EXISTS uq_auth_identity_provider_sub
ON auth_identity(provider, sub);

UPDATE auth_identity ai
SET email = u.email
FROM users u
WHERE ai.user_id = u.id AND ai.email IS NULL;

ALTER TABLE auth_identity
ALTER COLUMN email SET NOT NULL;