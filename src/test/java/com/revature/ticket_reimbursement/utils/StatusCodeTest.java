package com.revature.ticket_reimbursement.utils;

import org.junit.jupiter.api.Assertions;

public class StatusCodeTest {
    public static void assertEquals(int expectedCode, int actualCode) {
        Assertions.assertEquals(expectedCode, actualCode, String.format("Expected Status Code: %d. Actual Code: %d",
                expectedCode, actualCode));
    }
}
