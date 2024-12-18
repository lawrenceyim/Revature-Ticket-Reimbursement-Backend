USE ticket_reimbursement;
INSERT INTO account VALUES(1, 'USER_STORY_MANAGER', 'FirstName1', 'LastName1', 'password', 'user_story_manager');
INSERT INTO account VALUES(2, 'FINANCE_MANAGER', 'FirstName2', 'LastName2', 'password', 'finance_manager');
INSERT INTO account VALUES(3, 'EMPLOYEE', 'FirstName3', 'LastName3', 'password', 'employee');

INSERT INTO ticket VALUES(1, 3, 'Test example of an approved ticket.', 'TRAVEL', 'APPROVED', 999.99);
INSERT INTO ticket VALUES(2, 3, 'Test example of a denied ticket.', 'TRAVEL', 'DENIED', 999.99);
INSERT INTO ticket VALUES(3, 3, 'Test example of a pending ticket.', 'TRAVEL', 'PENDING', 999.99);

