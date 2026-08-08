CREATE TABLE applications (
      id SERIAL PRIMARY KEY,
      user_id INT NOT NULL,
      company_id INT NOT NULL,
      job_title VARCHAR(100) NOT NULL,
      job_description TEXT,
      date_applied TIMESTAMP NOT NULL,
      source VARCHAR(50),
      min_salary INTEGER,
      max_salary INTEGER,
      currency VARCHAR(3),
      location VARCHAR(200),
      status VARCHAR(20) CHECK (status IN ('Applied', 'Under Process', 'Interview', 'Offer', 'Rejected', 'Withdrawn')),
      created_at TIMESTAMP default CURRENT_TIMESTAMP,

      CONSTRAINT unique_application UNIQUE(user_id, company_id, job_title, date_applied),

      CONSTRAINT fk_user
          FOREIGN KEY (user_id)
              REFERENCES users(id),

      CONSTRAINT fk_company
          FOREIGN KEY (company_id)
              REFERENCES companies(id)

);