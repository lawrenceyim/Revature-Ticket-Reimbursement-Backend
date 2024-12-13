package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.exception.BadRequestException;
import com.revature.ticket_reimbursement.service.EmployeeService;
import com.revature.ticket_reimbursement.service.FinanceManagerService;
import org.json.JSONException;
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

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findAllTickets());
    }

    @PostMapping("/")
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        try {
            Ticket createdTicket = employeeService.createTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(createdTicket);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PatchMapping("/")
    public ResponseEntity<Ticket> updateTicket(@RequestBody Ticket ticket) {
        try {
            Ticket updatedTicket = financeManagerService.updateTicket(ticket);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<List<Ticket>> getTicketsByAccountId(@PathVariable int accountId) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAllTickets(accountId));
    }

    @GetMapping("/accounts/{accountId}/{status}")
    public ResponseEntity<List<Ticket>> getAllTicketsByAccountIdAndStatus(@PathVariable int accountId,
                                                                          @PathVariable TicketStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findTicketsByStatus(accountId, status));
    }


    @GetMapping("/approved")
    public ResponseEntity<List<Ticket>> getAllApprovedTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findTicketsByStatus(TicketStatus.APPROVED));
    }

    @GetMapping("/denied")
    public ResponseEntity<List<Ticket>> getAllDeniedTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findTicketsByStatus(TicketStatus.DENIED));
    }

    @GetMapping("/next")
    public ResponseEntity<Ticket> getNextPendingTicket() throws JSONException {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findNextPendingTicket());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Ticket>> getAllPendingTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.findTicketsByStatus(TicketStatus.PENDING));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable int ticketId) {
        Ticket ticket = financeManagerService.findTicketById(ticketId);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }
}
