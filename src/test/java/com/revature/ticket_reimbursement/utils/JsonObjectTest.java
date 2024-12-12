package com.revature.ticket_reimbursement.utils;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

public class JsonObjectTest {
    public static void assertEquals(JSONObject expected, JSONObject actual) {
        // .equals() doesn't work correctly when the JSON string is the same.
        // Ignore IDE suggestion to simplify using equal()
        Assertions.assertTrue(expected.toString().equals(actual.toString()),
                "Expected:\n" + expected + "\nActual:\n" + actual);
    }
}
