CREATE TABLE rounds (
   id SERIAL PRIMARY KEY,
   application_id INTEGER NOT NULL,
   round VARCHAR(100) NOT NULL,
   interview_date TIMESTAMP,
   status VARCHAR(20) CHECK (status IN ('Scheduled', 'Completed', 'Passed', 'Failed')),
   notes TEXT,

   CONSTRAINT unique_round UNIQUE(application_id, round, interview_date),

   CONSTRAINT fk_application
       FOREIGN KEY (application_id)
           REFERENCES applications(id)

);