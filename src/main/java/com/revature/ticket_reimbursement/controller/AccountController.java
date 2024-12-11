package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.AccountRepository;
import com.revature.ticket_reimbursement.service.AccountService;
import com.revature.ticket_reimbursement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/register")
    private ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
