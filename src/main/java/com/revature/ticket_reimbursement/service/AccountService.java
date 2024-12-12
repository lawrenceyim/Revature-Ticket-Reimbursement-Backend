package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.enums.EmployeeRole;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) throws BadRequestException {
        if (!account.isPasswordValid() ||
                !account.isUsernameValid() ||
                !account.isFirstNameValid() ||
                !account.isLastNameValid()) {
            throw new BadRequestException("Invalid account details.");
        }

        Optional<Account> accountInDatabase = accountRepository.findByUsername(account.getUsername());
        if (accountInDatabase.isPresent()) {
            throw new BadRequestException("Account with username already exists.");
        }

        account.setEmployeeRole(EmployeeRole.EMPLOYEE);
        return accountRepository.save(account);
    }

    public Account getAccountByUsernameAndPassword(Account account) throws BadRequestException {
        if (!account.isUsernameValid() || !account.isPasswordValid()) {
            throw new BadRequestException("Invalid login credentials.");
        }
        Optional<Account> foundAccount = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (foundAccount.isEmpty()) {
            throw new BadRequestException("Invalid login credentials.");
        }
        return foundAccount.get();
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
