CREATE TABLE application_skills (
     application_id INTEGER NOT NULL,
     skill_id INTEGER NOT NULL,

     CONSTRAINT fk_application
         FOREIGN KEY (application_id)
             REFERENCES applications(id),

     CONSTRAINT fk_skill
         FOREIGN KEY (skill_id)
             REFERENCES skills(id),

     PRIMARY KEY (application_id, skill_id)
);