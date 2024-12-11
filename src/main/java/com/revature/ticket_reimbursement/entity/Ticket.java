package com.revature.ticket_reimbursement.entity;

import com.revature.ticket_reimbursement.TicketStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Column(name = "ticket_id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ticketId;
    @Column(name = "made_by")
    private Integer madeBy;
    @Column(name = "description")
    private String description;
    @Column(name = "reimbursement_type")
    private String reimbursementType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;
    @Column(name = "reimbursement_amount")
    private BigDecimal reimbursementAmount;

    public Ticket() {
    }

    public Ticket(Integer ticketId, Integer madeBy, String description, String reimbursementType,
                  TicketStatus status, BigDecimal reimbursementAmount) {
        this.ticketId = ticketId;
        this.madeBy = madeBy;
        this.description = description;
        this.reimbursementType = reimbursementType;
        this.status = status;
        this.reimbursementAmount = reimbursementAmount;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(Integer madeBy) {
        this.madeBy = madeBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(String reimbursementType) {
        this.reimbursementType = reimbursementType;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public BigDecimal getReimbursementAmount() {
        return reimbursementAmount;
    }

    public void setReimbursementAmount(BigDecimal reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId.equals(ticket.ticketId) &&
                madeBy.equals(ticket.madeBy) &&
                description.equals(ticket.description) &&
                reimbursementType.equals(ticket.reimbursementType) &&
                status.equals(ticket.status) &&
                reimbursementAmount.equals(ticket.reimbursementAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, madeBy, description, reimbursementType, status, reimbursementAmount);
    }
}
