package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

   public Account createAccount(Account account) throws BadRequestException {
       if (!account.isPasswordValid() || !account.isUsernameValid()) {
           throw new BadRequestException("Invalid password or username.");
       }

       Optional<Account> accountInDatabase = accountRepository.findByUsername(account.getUsername());
       if (accountInDatabase.isPresent()) {
            throw new BadRequestException("Account with username already exists.");
       }

       return accountRepository.save(account);
   }

}
