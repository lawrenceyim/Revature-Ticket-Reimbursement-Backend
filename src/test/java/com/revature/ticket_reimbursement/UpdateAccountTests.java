package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.enums.EmployeeRole;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateAccountTests {
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

    @ParameterizedTest
    @CsvSource({
            "9999, 'EMPLOYEE', 'FirstName9999', 'LastName9999', 'password' , 'admin_9999'",
            "9998, 'USER_STORY_MANAGER', 'FirstName9998', 'LastName9998', 'password', 'finance_manager_9998'",
            "9997, 'FINANCE_MANAGER', 'FirstName9997', 'LastName9997', 'password', 'employee_9997'"
    })
    public void updateAccountTest(int accountId, EmployeeRole employeeRole, String firstName, String lastName,
                                    String password, String username) throws IOException, InterruptedException {
        Account accountToUpdate = new Account(accountId, employeeRole, firstName, lastName, password, username);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts"))
                .method("PATCH", HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(accountToUpdate)))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);

        Account actualAccount = objectMapper.readValue(response.body(), Account.class);
        Assertions.assertEquals(accountToUpdate, actualAccount,
                "Expected: " + accountToUpdate + ". Actual: " + actualAccount);
    }
}
