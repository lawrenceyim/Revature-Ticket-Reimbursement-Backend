package com.revature.ticket_reimbursement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.ticket_reimbursement.utils.JsonObjectTest;
import com.revature.ticket_reimbursement.utils.StatusCodeTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegisterAccountTests {
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
    public void createValidAccountTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "password");
        jsonObject.put("username", "username1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(200, status);

        String expectedResponseJson = """
                {
                    "accountId": 1,
                    "employeeRole": "EMPLOYEE",
                    "firstName": "Lawrence",
                    "lastName": "Yim",
                    "password": "password",
                    "username": "username1"
                }
                """;
        JSONObject expectedJsonObject = new JSONObject(expectedResponseJson);
        JSONObject actualJsonObject = new JSONObject(response.body());
        JsonObjectTest.assertTrue(expectedJsonObject, actualJsonObject);
    }

    @Test
    public void createAccountWithInvalidUsernameTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "password");
        jsonObject.put("username", "usernam");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithDuplicateUsernameTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "password");
        jsonObject.put("username", "employee_9997");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithInvalidPasswordTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "passwor");
        jsonObject.put("username", "username1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithMissingFirstNameTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "password");
        jsonObject.put("username", "username1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithMissingLastNameTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("password", "password");
        jsonObject.put("username", "username1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithMissingPasswordTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("username", "username1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }

    @Test
    public void createAccountWithMissingUsernameTest() throws IOException, InterruptedException, JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "Lawrence");
        jsonObject.put("lastName", "Yim");
        jsonObject.put("password", "password");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/accounts/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        StatusCodeTest.assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }
}
