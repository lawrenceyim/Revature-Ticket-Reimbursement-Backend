package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.service.EmployeeService;
import com.revature.ticket_reimbursement.service.FinanceManagerService;
import com.revature.ticket_reimbursement.service.TicketService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        logger.info("DOESI T STILL WORK?");
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

    @GetMapping("/approved")
    public ResponseEntity<List<Ticket>> getApprovedTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTicketsByStatus(TicketStatus.APPROVED));
    }

    @GetMapping("/denied")
    public ResponseEntity<List<Ticket>> getDeniedTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTicketsByStatus(TicketStatus.DENIED));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Ticket>> getPendingTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(ticketService.findTicketsByStatus(TicketStatus.PENDING));
    }

    @GetMapping("/next")
    public ResponseEntity<Ticket> getNextPendingTicket() throws JSONException {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findNextPendingTicket());
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = ticketService.findTicketById(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }
}
