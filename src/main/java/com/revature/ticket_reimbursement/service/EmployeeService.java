package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private TicketRepository ticketRepository;
    public List<Ticket> getAllTickets(int accountId) {
        return ticketRepository.findAllByAccountId(accountId);
    }
}
