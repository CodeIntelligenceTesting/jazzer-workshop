package com.example;

import com.code_intelligence.jazzer.api.FuzzedDataProvider;
import com.code_intelligence.jazzer.junit.FuzzTest;
import org.junit.jupiter.api.Assertions;

class FuzzTests {
    @FuzzTest(
            // Specify the maximum duration of a fuzzing run. Default:
            // maxDuration = "1m"
    )
    void myFuzzTest(FuzzedDataProvider data) {
        // Use data to generate arguments for a function you want to test.
        // If the method is expected to throw any exceptions, catch them.
        int someNumber = data.consumeInt();
        String someString = data.consumeRemainingAsString();
        // You can also use JUnit assertions to verify that the function behaves as expected even
        // on malicious input.
        if (Main.checkSecret(someNumber)) {
            Assertions.assertNotEquals("Hello, Jazzer!", someString);
        }
    }
}
