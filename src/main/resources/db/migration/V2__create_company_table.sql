CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    website TEXT,
    industry VARCHAR(100),
    notes TEXT,
    career_page_url TEXT,
    created_at TIMESTAMP default CURRENT_TIMESTAMP
);