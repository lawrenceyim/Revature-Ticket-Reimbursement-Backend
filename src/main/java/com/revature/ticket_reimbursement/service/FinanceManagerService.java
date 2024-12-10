package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.TicketStatus;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceManagerService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllTicketsByStatus(TicketStatus status) {
        return ticketRepository.findAllByTicketStatus(status);
    }

    // add in concurrency or figure out a way to make sure multiple finance managers don't work on the same ticket
    // probably use a queue
    public Ticket getNextPendingTicket() {
        return null;
    }
}
