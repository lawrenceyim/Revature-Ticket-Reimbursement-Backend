DROP TABLE IF EXISTS account; -- user is a reserved key word in SQL
DROP TABLE IF EXISTS ticket;

CREATE TABLE account (
    account_id INT AUTO_INCREMENT,
    employee_role VARCHAR(30) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (account_id)
);

CREATE TABLE ticket (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    decided_by INT,
    made_by INT NOT NULL,
    description VARCHAR(1000),
    reimbursement_type VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    reimbursement_amount DECIMAL(20, 2) NOT NULL,
    decided_on DATETIME,
    made_on DATETIME NOT NULL,
    FOREIGN KEY (decided_by) REFERENCES account(account_id),
    FOREIGN KEY (made_by) REFERENCES account(account_id)
);

-- Starting test values with ids of 9999 to avoid test issues
INSERT INTO account VALUES(9999, 'UserStoryManager', 'FirstName1', 'LastName1', 'password', 'admin_1');
INSERT INTO account VALUES(9998, 'FinanceManager', 'FirstName2', 'LastName2', 'password', 'finance_manager_1');
INSERT INTO account VALUES(9997, 'Employee', 'FirstName3', 'LastName3', 'password', 'employee_1');

INSERT INTO ticket VALUES(9999, 9998, 9997, 'Test example of an approved ticket.', 'TRAVEL', 'APPROVED', 999.99,
    '2024-10-02 12:00:00', '2024-10-01 12:00:00');
INSERT INTO ticket VALUES(9998, 9998, 9997, 'Test example of a denied ticket.', 'TRAVEL', 'DENIED', 999.99,
    '2024-11-02 12:00:00', '2024-11-01 12:00:00');
INSERT INTO ticket VALUES(9997, NULL, 9997, 'Test example of a pending ticket.', 'TRAVEL', 'PENDING', 999.99,
    NULL, '2024-09-01 12:00:00');

