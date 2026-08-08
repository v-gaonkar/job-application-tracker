ALTER TABLE email_templates ADD COLUMN
    category VARCHAR(30) NOT NULL CHECK (category IN ('recruiter_followup', 'system_reminder'));