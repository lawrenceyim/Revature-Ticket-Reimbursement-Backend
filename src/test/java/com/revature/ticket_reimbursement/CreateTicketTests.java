package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.entity.Ticket;
import com.revature.ticket_reimbursement.enums.ReimbursementType;
import com.revature.ticket_reimbursement.enums.TicketStatus;
import com.revature.ticket_reimbursement.utils.JsonResponseTest;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

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
        String createTicketJson = """
                {
                    "madeBy": 9998,
                    "description": "Hotel.",
                    "reimbursementType": "LODGING",
                    "reimbursementAmount": "100"
                }""";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/tickets/"))
                .POST(HttpRequest.BodyPublishers.ofString(createTicketJson))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);
        String expectedResponseJson = """
                {
                    "ticketId": 1,
                    "madeBy": 9998,
                    "description": "Hotel.",
                    "reimbursementType": "LODGING",
                    "status": "PENDING",
                    "reimbursementAmount": 100
                }""";
        JSONObject expectedJsonObject = new JSONObject(expectedResponseJson);
        JSONObject actualJsonObject = new JSONObject(response.body());
        JsonResponseTest.assertTrue(expectedJsonObject, actualJsonObject);
    }
}
