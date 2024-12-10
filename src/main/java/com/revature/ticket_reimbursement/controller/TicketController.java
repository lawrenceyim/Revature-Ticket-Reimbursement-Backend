package com.revature.ticket_reimbursement.controller;

import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.service.EmployeeService;
import com.revature.ticket_reimbursement.service.FinanceManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private FinanceManagerService financeManagerService;

    @GetMapping("/")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.status(HttpStatus.OK).body(financeManagerService.getAllTickets());
    }

}
