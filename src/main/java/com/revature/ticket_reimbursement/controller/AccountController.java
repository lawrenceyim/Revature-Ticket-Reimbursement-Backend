package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.service.AccountService;
import com.revature.ticket_reimbursement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
@CrossOrigin(origins = "http://localhost:5173/")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @GetMapping("")
    private ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.findAllAccounts());
    }

    @PatchMapping("")
    private ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(account));
        } catch (BadRequestException e) {
            logger.info("Bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<Account> login(@RequestBody Account account) {
        try {
            Account accountInDatabase = accountService.findAccountByUsernameAndPassword(account);
            return ResponseEntity.status(HttpStatus.OK).body(accountInDatabase);
        } catch (BadRequestException e) {
            logger.info("Bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    @PostMapping("/register")
    private ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
        } catch (BadRequestException e) {
            logger.info("Bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
