package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.service.EmployeeService;
import com.revature.ticket_reimbursement.service.FinanceManagerService;
import com.revature.ticket_reimbursement.service.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173/")
public class TicketController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FinanceManagerService financeManagerService;
    @Autowired
    private TicketService ticketService;
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    @GetMapping("")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTickets());
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket createdTicket = employeeService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(createdTicket);
        } catch (BadRequestException e) {
            logger.info("Bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        try {
            Ticket updatedTicket = financeManagerService.updateTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
        } catch (BadRequestException e) {
            logger.info("Bad request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Ticket>> getTicketsByAccountId(@PathVariable int accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTicketsByAccountId(accountId));
    }

    @GetMapping("/accounts/{accountId}/{status}")
    public ResponseEntity<List<Ticket>> getAllTicketsByAccountIdAndStatus(@PathVariable int accountId,
                                                                          @PathVariable String status) {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService
                .findTicketsByAccountIdAndStatus(accountId, TicketStatus.valueOf(status.toUpperCase())));
    }

    @GetMapping("/id/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @GetMapping("/next")
    public ResponseEntity<Ticket> getNextPendingTicket() {
        Ticket ticket = ticketService.findNextPendingTicket();
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Ticket>> getTicketByStatus(@PathVariable String status) {
        TicketStatus ticketStatus = TicketStatus.valueOf(status.toUpperCase());
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTicketsByStatus(ticketStatus));
    }
}
