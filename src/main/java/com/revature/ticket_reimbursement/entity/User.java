package com.revature.ticket_reimbursement.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;
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
