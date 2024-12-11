package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/register")
    private ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return null; // TODO Add registration service
    }
}
