package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.ReimbursementType;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CreateTicketTests {
    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[]{};
        app = SpringApplication.run(TicketReimbursementApplication.class, args);
        Thread.sleep(500);
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(500);
        SpringApplication.exit(app);
    }

    @Test
    public void createValidTicketTest() throws IOException, InterruptedException, JSONException {
        Ticket ticketToCreate = new Ticket(null, 9998, "Hotel.", ReimbursementType.LODGING,
                null, new BigDecimal("100"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tickets/"))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(ticketToCreate)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);

        Ticket expectedTicket = new Ticket(1, 9998, "Hotel.", ReimbursementType.LODGING,
                TicketStatus.PENDING, new BigDecimal("100"));
        Ticket actualTicket = objectMapper.readValue(response.body(), Ticket.class);
        Assertions.assertEquals(expectedTicket, actualTicket,
                "Expected: " + expectedTicket + ". Actual: " + actualTicket);
    }

    @Test
    public void createTicketWithInvalidAmountTest() throws IOException, InterruptedException {
        Ticket ticketToCreate = new Ticket(null, 9998, "Hotel.", ReimbursementType.LODGING,
                null, new BigDecimal("0"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tickets/"))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(ticketToCreate)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createTicketWithInvalidTypeTest() throws IOException, InterruptedException {
        Ticket ticketToCreate = new Ticket(null, 9998, "Hotel.", null,
                null, new BigDecimal("0"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tickets/"))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(ticketToCreate)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createTicketWithInvalidDescriptionTest() throws IOException, InterruptedException {
        Ticket ticketToCreate = new Ticket(null, 9998, "", ReimbursementType.LODGING,
                null, new BigDecimal("0"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tickets/"))
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(ticketToCreate)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }
}
