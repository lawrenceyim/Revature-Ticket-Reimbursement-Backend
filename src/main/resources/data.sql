DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS ticket;

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

-- Starting test values with ids of 9999 to avoid test issues
INSERT INTO account VALUES(9999, 'USER_STORY_MANAGER', 'FirstName9999', 'LastName9999', 'password', 'admin_9999');
INSERT INTO account VALUES(9998, 'FINANCE_MANAGER', 'FirstName9998', 'LastName9998', 'password', 'finance_manager_9998');
INSERT INTO account VALUES(9997, 'EMPLOYEE', 'FirstName9997', 'LastName9997', 'password', 'employee_9997');

INSERT INTO ticket VALUES(9999, 9997, 'Test example of an approved ticket.', 'TRAVEL', 'APPROVED', 999.99);
INSERT INTO ticket VALUES(9998, 9997, 'Test example of a denied ticket.', 'TRAVEL', 'DENIED', 999.99);
INSERT INTO ticket VALUES(9997, 9997, 'Test example of a pending ticket.', 'TRAVEL', 'PENDING', 999.99);

