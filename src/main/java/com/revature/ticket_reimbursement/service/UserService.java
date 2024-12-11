package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AccountRepository accountRepository;


}
