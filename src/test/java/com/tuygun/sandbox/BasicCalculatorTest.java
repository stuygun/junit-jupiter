package com.tuygun.sandbox;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName("Calculator Test")
class BasicCalculatorTest {
    private final BasicCalculator calculator = new BasicCalculator();

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        System.out.println("After Each");
    }

    @Nested
    @DisplayName("Addition Related Tests")
    class AdditionTest {
        @Test
        @DisplayName("Check Calculator Add Functionality")
        void testAddFunction() {
            assertEquals(2.0,
                    calculator.add(1.0, 1.0), "Add functionality is not working");
        }
    }

    @Nested
    @DisplayName("Subtraction Related Tests")
    class SubtractTest {
        @Test
        @DisplayName("Basic Subtract Functionality-1")
        void testBasicSubtractFunction() {
            assertEquals(0.0, calculator.subtract(1.0, 1.0));
        }

        @Test
        @DisplayName("Basic Subtract Functionality-2")
        void testSubtractFunction() {
            assertNotEquals(1.0, calculator.subtract(1.0, 1.0));
        }
    }
}
