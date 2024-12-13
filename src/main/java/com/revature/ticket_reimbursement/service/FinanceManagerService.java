package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

@Service
public class FinanceManagerService {
    @Autowired
    private TicketRepository ticketRepository;
    private final Queue<Integer> pendingTicketIdQueue = new ArrayDeque<>();

    public void addPendingTicketToQueue(int ticketId) {
        pendingTicketIdQueue.add(ticketId);
    }

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket findNextPendingTicket() {
        // Check if the ticket queue is empty when a user requests a pending ticket
        // The queue cannot be filled at instantiation of the service component because it results in the queue being empty.
        // Possible reason: service is instantiated before the H2 database is instantiated.
        // Possible issue: this may result in multiple users getting the same pending ticket.
        // Possible fix: add a service that returns a ticket to the queue only after a set period of time if it still
        // has a PENDING status.
        if (pendingTicketIdQueue.isEmpty()) {
            List<Ticket> pendingTickets = ticketRepository.findAllByTicketStatus(TicketStatus.PENDING);
            for (Ticket ticket : pendingTickets) {
                pendingTicketIdQueue.add(ticket.getTicketId());
            }
        }
        while (!pendingTicketIdQueue.isEmpty()) {
            int ticketID = pendingTicketIdQueue.peek();
            Ticket ticket = ticketRepository.findById(ticketID).get();
            if (!ticket.getStatus().equals(TicketStatus.PENDING)) {
                continue;
            }
            return ticket;
        }
        return null;
    }

    public Ticket findTicketById(int ticketId) {
        return ticketRepository.findById(ticketId).get();
    }

    public List<Ticket> findTicketsByStatus(TicketStatus status) {
        return ticketRepository.findAllByTicketStatus(status);
    }
    
    public Ticket updateTicket(Ticket ticket) throws BadRequestException {
        if (ticket.getTicketId() == null) {
            throw new BadRequestException("Ticket must have an ID.");
        }

        Optional<Ticket> ticketToUpdateOptional = ticketRepository.findById(ticket.getTicketId());
        if (ticketToUpdateOptional.isEmpty()) {
            throw new BadRequestException("Ticket with provided ID does not exist.");
        }

        Ticket ticketToUpdate = ticketToUpdateOptional.get();
        if (!ticketToUpdate.getStatus().equals(TicketStatus.PENDING)) {
            throw new BadRequestException("Cannot update ticket after it has been approved or denied.");
        }

        if (ticket.getStatus().equals(TicketStatus.PENDING)) {
            throw new BadRequestException("Cannot update a ticket's status to pending.");
        }

        if (!ticket.getDescription().equals(ticketToUpdate.getDescription()) ||
                !ticket.getMadeBy().equals(ticketToUpdate.getMadeBy()) ||
                !ticket.getReimbursementAmount().equals(ticketToUpdate.getReimbursementAmount()) ||
                !ticket.getReimbursementType().equals(ticketToUpdate.getReimbursementType())) {
            throw new BadRequestException("Cannot change ticket details besides its status.");
        }

        return ticketRepository.save(ticket);
    }
}
