USE ticket_reimbursement;
DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS account;

CREATE TABLE account (
    account_id INT AUTO_INCREMENT,
    employee_role VARCHAR(30) NOT NULL DEFAULT 'EMPLOYEE',
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL, -- Min 8 characters, Max 100 characters
    username VARCHAR(50) NOT NULL UNIQUE, -- Min 8 characters, Max 50 characters
    PRIMARY KEY (account_id)
);

CREATE TABLE ticket (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    made_by INT NOT NULL,
    description VARCHAR(1000) NOT NULL,
    reimbursement_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    reimbursement_amount DECIMAL(20, 2) NOT NULL,
    FOREIGN KEY (made_by) REFERENCES account(account_id)
);
