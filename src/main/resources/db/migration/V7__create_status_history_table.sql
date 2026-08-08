CREATE TABLE status_history (
    id SERIAL PRIMARY KEY,
    application_id INTEGER NOT NULL,
    new_status VARCHAR(20) NOT NULL,
    old_status VARCHAR(20) ,
    changed_at TIMESTAMP default CURRENT_TIMESTAMP,

    CONSTRAINT fk_application
        FOREIGN KEY (application_id)
            REFERENCES applications(id)

);