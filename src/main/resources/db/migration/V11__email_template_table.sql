CREATE TABLE email_templates (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    subject TEXT NOT NULL,
    body TEXT NOT NULL
);