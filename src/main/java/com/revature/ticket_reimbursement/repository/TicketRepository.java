package com.revature.ticket_reimbursement.repository;

import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query("SELECT t FROM Ticket t WHERE t.status = :status")
    List<Ticket> findAllByTicketStatus(@Param("status")TicketStatus status);

    @Query("SELECT t FROM Ticket t WHERE t.madeBy = :madeBy")
    List<Ticket> findAllByAccountId(@Param("madeBy")int madeBy);

    @Query("SELECT t FROM Ticket t WHERE t.madeBy = :madeBy AND t.status = :status")
    List<Ticket> findAllByAccountIdAndTicketStatus(@Param("madeBy") int madeBy, @Param("status") TicketStatus status);

    @Query(value = """
            SELECT *
            FROM ticket
            WHERE ticket.status = 'PENDING'
            ORDER BY ticket.ticket_id ASC
            LIMIT 1;""", nativeQuery = true)
    Optional<Ticket> findNextPendingTicket();
}
