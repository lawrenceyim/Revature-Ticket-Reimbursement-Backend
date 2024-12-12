package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.utils.JsonObjectTest;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
            "9999,USER_STORY_MANAGER,FirstName9999,LastName9999,password,admin_9999",
            "9998,FINANCE_MANAGER,FirstName9998,LastName9998,password,finance_manager_9998",
            "9997,EMPLOYEE,FirstName9997,LastName9997,password,employee_9997"})
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

        JSONObject expectedJsonObject = new JSONObject();
        expectedJsonObject.put("accountId", accountId);
        expectedJsonObject.put("employeeRole", employeeRole);
        expectedJsonObject.put("firstName", firstName);
        expectedJsonObject.put("lastName", lastName);
        expectedJsonObject.put("password", password);
        expectedJsonObject.put("username", username);

        JSONObject actualJsonObject = new JSONObject(response.body());

        JsonObjectTest.assertEquals(expectedJsonObject, actualJsonObject);
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
