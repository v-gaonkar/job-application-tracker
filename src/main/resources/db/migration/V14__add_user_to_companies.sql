ALTER TABLE companies ADD COLUMN user_id INTEGER;

ALTER TABLE companies DROP CONSTRAINT companies_name_key;

ALTER TABLE companies ADD CONSTRAINT fk_user
    FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE companies ADD CONSTRAINT unique_company_per_user UNIQUE (name, user_id);