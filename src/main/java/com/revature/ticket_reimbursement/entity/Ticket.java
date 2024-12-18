package com.revature.ticket_reimbursement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.enums.ReimbursementType;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Column(name = "ticket_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ticketId;
    @Column(name = "made_by")
    private Integer madeBy;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "reimbursement_type")
    private ReimbursementType reimbursementType;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;
    @Column(name = "reimbursement_amount")
    private BigDecimal reimbursementAmount;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    public Ticket() {
    }

    public Ticket(Integer madeBy, String description, ReimbursementType reimbursementType, BigDecimal reimbursementAmount) {
        this.madeBy = madeBy;
        this.description = description;
        this.reimbursementType = reimbursementType;
        this.reimbursementAmount = reimbursementAmount;
    }

    public Ticket(Integer ticketId, Integer madeBy, String description, ReimbursementType reimbursementType,
                  TicketStatus status, BigDecimal reimbursementAmount) {
        this.ticketId = ticketId;
        this.madeBy = madeBy;
        this.description = description;
        this.reimbursementType = reimbursementType;
        this.status = status;
        this.reimbursementAmount = reimbursementAmount;
    }

    @JsonIgnore
    public boolean isAmountValid() {
        if (reimbursementAmount == null) {
            return false;
        }
        // Check if the amount is bigger than 0.
        return reimbursementAmount.compareTo(new BigDecimal("0")) > 0;
    }

    @JsonIgnore
    public boolean isDescriptionValid() {
        if (description == null) {
            return false;
        }
        return !description.isEmpty() && description.length() <= MAX_DESCRIPTION_LENGTH;
    }

    @JsonIgnore
    public boolean amountMatches(BigDecimal amount) {
        return reimbursementAmount.compareTo(amount) == 0;
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

    public ReimbursementType getReimbursementType() {
        return reimbursementType;
    }

    public void setReimbursementType(ReimbursementType reimbursementType) {
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
                this.amountMatches(ticket.getReimbursementAmount()) &&
                status.equals(ticket.status) &&
                reimbursementAmount.equals(ticket.reimbursementAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, madeBy, description, reimbursementType, status, reimbursementAmount);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException ignored) {
            return "Unable to convert ticket data to JSON.";
        }
    }
}
