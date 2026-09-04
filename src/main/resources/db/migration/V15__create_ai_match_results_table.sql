CREATE TABLE ai_match_results (
  id SERIAL PRIMARY KEY,
  application_id INTEGER NOT NULL,
  match_score INTEGER,
  interview_tips TEXT,
  generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  CONSTRAINT fk_application
      FOREIGN KEY (application_id)
          REFERENCES applications(id) ON DELETE CASCADE
);