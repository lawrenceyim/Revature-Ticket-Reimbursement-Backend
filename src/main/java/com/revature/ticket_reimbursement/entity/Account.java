package com.revature.ticket_reimbursement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.ticket_reimbursement.enums.EmployeeRole;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {
    @Column(name = "account_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int accountId;
    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private EmployeeRole employeeRole;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "password")
    private String password;
    @Column(name = "username")
    private String username;

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 100;
    private static final int MIN_USERNAME_LENGTH = 8;
    private static final int MAX_USERNAME_LENGTH = 50;

    public Account() {
    }

    public Account(String firstName, String lastName, String password, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }

    public Account(int accountId, EmployeeRole employeeRole, String firstName, String lastName, String password,
                   String username) {
        this.accountId = accountId;
        this.employeeRole = employeeRole;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
    }

    @JsonIgnore
    public boolean isPasswordValid() {
        if (password == null) {
            return false;
        }
        return password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH;
    }

    @JsonIgnore
    public boolean isUsernameValid() {
        if (username == null) {
            return false;
        }
        return username.length() >= MIN_USERNAME_LENGTH && username.length() <= MAX_USERNAME_LENGTH;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                Objects.equals(employeeRole, account.employeeRole) &&
                Objects.equals(firstName, account.firstName) &&
                Objects.equals(lastName, account.lastName) &&
                Objects.equals(password, account.password) &&
                Objects.equals(username, account.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, employeeRole, firstName, lastName, password, username);
    }
}
