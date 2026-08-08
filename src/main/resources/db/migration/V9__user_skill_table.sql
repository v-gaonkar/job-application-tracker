CREATE TABLE user_skills (
    user_id INTEGER NOT NULL,
    skill_id INTEGER NOT NULL,

         CONSTRAINT fk_user
             FOREIGN KEY (user_id)
                 REFERENCES users(id),

         CONSTRAINT fk_skill
             FOREIGN KEY (skill_id)
                 REFERENCES skills(id),

        PRIMARY KEY (user_id, skill_id)
);