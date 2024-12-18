package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.enums.EmployeeRole;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class GetAccountTests {
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
    public void getAllAccountsTest() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/accounts"))
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);

        List<Account> expectedResult = List.of(
                new Account(9997, EmployeeRole.EMPLOYEE, "FirstName9997",
                        "LastName9997", "password", "employee_9997"),
                new Account(9998, EmployeeRole.FINANCE_MANAGER, "FirstName9998",
                        "LastName9998", "password", "finance_manager_9998"),
                new Account(9999, EmployeeRole.USER_STORY_MANAGER, "FirstName9999",
                        "LastName9999", "password", "admin_9999")
        );
        List<Account> actualResult = objectMapper.readValue(response.body(), new TypeReference<>() {
        });
        Assertions.assertEquals(expectedResult, actualResult,
                "Expected: " + expectedResult + ". Actual: " + actualResult);

    }
}
