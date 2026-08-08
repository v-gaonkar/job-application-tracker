CREATE TABLE notification_logs (
    id SERIAL PRIMARY KEY,
    application_id INTEGER NOT NULL,
    round_id INTEGER,
    type VARCHAR(30) NOT NULL CHECK (type IN ('stale-reminder', 'interview-reminder', 'follow-up-sent')),
    sent_at TIMESTAMP default CURRENT_TIMESTAMP,

    CONSTRAINT fk_application
        FOREIGN KEY (application_id)
            REFERENCES applications(id),

    CONSTRAINT fk_round
        FOREIGN KEY (round_id)
            REFERENCES rounds(id)

);