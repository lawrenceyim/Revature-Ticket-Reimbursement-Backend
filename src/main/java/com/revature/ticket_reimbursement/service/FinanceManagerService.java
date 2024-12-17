package com.revature.ticket_reimbursement.service;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FinanceManagerService {
    @Autowired
    private TicketRepository ticketRepository;

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
                !ticket.amountMatches(ticketToUpdate.getReimbursementAmount()) ||
                !ticket.getReimbursementType().equals(ticketToUpdate.getReimbursementType())) {
            throw new BadRequestException("Cannot change ticket details besides its status.");
        }

        return ticketRepository.save(ticket);
    }
}
