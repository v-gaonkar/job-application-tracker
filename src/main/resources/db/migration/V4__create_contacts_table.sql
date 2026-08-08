CREATE TABLE contacts (
   id SERIAL PRIMARY KEY,
   company_id INT NOT NULL,
   name VARCHAR(100) NOT NULL,
   email VARCHAR(100),
   role VARCHAR(50),
   phone_no VARCHAR(20),
   notes TEXT,
   created_at TIMESTAMP default CURRENT_TIMESTAMP,
   UNIQUE (company_id, email),

   CONSTRAINT fk_company
       FOREIGN KEY (company_id)
                       REFERENCES companies(id)

);