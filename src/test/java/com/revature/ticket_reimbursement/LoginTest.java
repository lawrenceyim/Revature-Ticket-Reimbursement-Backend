package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.entity.Account;
import com.revature.ticket_reimbursement.enums.EmployeeRole;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginTest {
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
            "1,USER_STORY_MANAGER,FirstName1,LastName1,password,user_story_manager",
            "2,FINANCE_MANAGER,FirstName2,LastName2,password,finance_manager",
            "3,EMPLOYEE,FirstName3,LastName3,password,employee"})
    public void loginWithValidCredentialTest(int accountId, String employeeRole, String firstName, String lastName,
                                             String password, String username)
            throws JSONException, IOException, InterruptedException {
        JSONObject loginJsonObject = new JSONObject();
        loginJsonObject.put("username", username);
        loginJsonObject.put("password", password);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/accounts/login"))
                .POST(HttpRequest.BodyPublishers.ofString(loginJsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);

        Account expectedAccount = new Account(accountId, EmployeeRole.valueOf(employeeRole), firstName, lastName, password, username);
        Account actualAccount = objectMapper.readValue(response.body(), Account.class);
        Assertions.assertEquals(expectedAccount, actualAccount,
                "Expected: " + expectedAccount + ". Actual: " + actualAccount);
    }

    @ParameterizedTest
    @CsvSource({
            "password,EMPLOYEE_9996",
            "password,EM"
    })
    public void loginWithInvalidUsernameTest(String password, String username)
            throws JSONException, IOException, InterruptedException {
        JSONObject loginJsonObject = new JSONObject();
        loginJsonObject.put("username", username);
        loginJsonObject.put("password", password);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/accounts/login"))
                .POST(HttpRequest.BodyPublishers.ofString(loginJsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @ParameterizedTest
    @CsvSource({
            "password10,EMPLOYEE_9997",
            "pass,EMPLOYEE_9997"
    })
    public void loginWithInvalidPasswordTest(String password, String username)
            throws JSONException, IOException, InterruptedException {
        JSONObject loginJsonObject = new JSONObject();
        loginJsonObject.put("username", username);
        loginJsonObject.put("password", password);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/accounts/login"))
                .POST(HttpRequest.BodyPublishers.ofString(loginJsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }
}
