CREATE TABLE password_reset_tokens (
   id SERIAL PRIMARY KEY,
   user_id INTEGER NOT NULL,
   token TEXT NOT NULL,
   expires_at TIMESTAMP NOT NULL,
   used BOOLEAN NOT NULL DEFAULT FALSE,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
   CONSTRAINT uq_password_reset_token UNIQUE (token)
);