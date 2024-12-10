package com.revature.ticket_reimbursement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Column(name = "account_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    @Column(name = "employee_role")
    private String employeeRole;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;
}
