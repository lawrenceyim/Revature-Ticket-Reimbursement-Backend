package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.AccountRepository;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FinanceManagerService financeManagerService;
    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) throws BadRequestException {
        Optional<Account> madeByAccount = accountRepository.findById(ticket.getMadeBy());
        if (madeByAccount.isEmpty()) {
            throw new BadRequestException("Associated account does not exist.");
        }

        if (!ticket.isAmountValid()) {
            throw new BadRequestException("Ticket must have a valid reimbursement amount.");
        }

        if (!ticket.isDescriptionValid()) {
            throw new BadRequestException("Ticket must have a valid description.");
        }

        if (ticket.getReimbursementType() == null) {
            throw new BadRequestException("Ticket must have a reimbursement type.");
        }

        ticket.setStatus(TicketStatus.PENDING);
        financeManagerService.addPendingTicketToQueue(ticket.getTicketId());
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets(int accountId) {
        return ticketRepository.findAllByAccountId(accountId);
    }
}
