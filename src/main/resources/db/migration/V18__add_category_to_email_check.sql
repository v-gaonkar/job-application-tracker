ALTER TABLE email_templates DROP CONSTRAINT email_templates_category_check;
ALTER TABLE email_templates ADD CONSTRAINT email_templates_category_check
    CHECK (category IN ('recruiter_followup', 'system_reminder', 'auth'));