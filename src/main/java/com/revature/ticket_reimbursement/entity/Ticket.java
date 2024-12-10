package com.revature.ticket_reimbursement.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Column(name = "ticket_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ticketId;
    @Column(name = "decided_by")
    private int decidedBy;
    @Column(name = "made_by")
    private int madeBy;
    @Column(name = "description")
    private String description;
    @Column(name = "reimbursement_type")
    private String reimbursementType;
    @Column(name = "status")
    private String status;
    @Column(name = "reimbursement_amount")
    private BigDecimal reimbursementAmount;
    @Column(name = "decided_on")
    private LocalDateTime decidedOn;
    @Column(name = "made_on")
    private LocalDateTime madeOn;
}
