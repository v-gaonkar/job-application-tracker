ALTER TABLE rounds DROP CONSTRAINT fk_application;
ALTER TABLE status_history DROP CONSTRAINT fk_application;
ALTER TABLE notification_logs DROP CONSTRAINT fk_application;
ALTER TABLE notification_logs DROP CONSTRAINT fk_round;
ALTER TABLE application_skills DROP CONSTRAINT fk_skill;
ALTER TABLE application_skills DROP CONSTRAINT fk_application;
ALTER TABLE contacts DROP CONSTRAINT fk_company;

ALTER TABLE rounds ADD CONSTRAINT fk_application
       FOREIGN KEY (application_id)
           REFERENCES applications(id) ON DELETE CASCADE;

ALTER TABLE status_history ADD CONSTRAINT fk_application
    FOREIGN KEY (application_id)
        REFERENCES applications(id) ON DELETE CASCADE;

ALTER TABLE notification_logs ADD CONSTRAINT fk_application
    FOREIGN KEY (application_id)
        REFERENCES applications(id) ON DELETE CASCADE;

ALTER TABLE notification_logs ADD CONSTRAINT fk_round
    FOREIGN KEY (round_id)
        REFERENCES rounds(id) ON DELETE CASCADE;

ALTER TABLE application_skills ADD CONSTRAINT fk_application
         FOREIGN KEY (application_id)
             REFERENCES applications(id);

ALTER TABLE application_skills ADD CONSTRAINT fk_skill
    FOREIGN KEY (skill_id)
        REFERENCES skills(id) ON DELETE CASCADE;

ALTER TABLE contacts ADD CONSTRAINT fk_company
    FOREIGN KEY (company_id)
        REFERENCES companies(id) ON DELETE CASCADE;