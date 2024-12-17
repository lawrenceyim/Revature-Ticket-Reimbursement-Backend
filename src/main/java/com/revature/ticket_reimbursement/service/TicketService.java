package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> findTicketsByAccountId(int accountId) {
        return ticketRepository.findAllByAccountId(accountId);
    }

    public List<Ticket> findTicketsByAccountIdAndStatus(int accountId, TicketStatus status) {
        return ticketRepository.findAllByAccountIdAndTicketStatus(accountId, status);
    }

    public Ticket findTicketById(int ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public List<Ticket> findTicketsByStatus(TicketStatus status) {
        return ticketRepository.findAllByTicketStatus(status);
    }
}
