package com.tuygun.sandbox;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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

    @DisplayName("Should pass a non-null string")
    @ParameterizedTest(name = "{index} -> message=''{0}''")
    @ValueSource(strings = {"test1", "test2"})
    void testStr(String message) {
        assertNotNull(message);
    }

    @Nested
    @DisplayName("Addition Related Tests")
    class AdditionTests {
        @Test
        @DisplayName("Check Calculator Add Functionality")
        void testAddFunction() {
            assertEquals(2,
                    calculator.add(1, 1), "Add functionality is not working");
        }

        @DisplayName("Addition Functionality Parameterized Test")
        @ParameterizedTest(name = "{index} -> number1={0}, number2={1}, result={2}")
        @CsvSource({"1, 1, 2", "2, 3, 5"})
        void testAddFunctionParameterized(int a, int b, int result) {
            assertEquals(result, calculator.add(a, b));
        }

        @Test
        @DisplayName("Integer Overflow")
        void integerOverFlow() {
            assertThrows(ArithmeticException.class, () -> calculator.add(Integer.MAX_VALUE, 1));
        }
    }

    @Nested
    @DisplayName("Subtraction Related Tests")
    class SubtractTests {
        @Test
        @DisplayName("Basic Subtract Functionality-1")
        void testBasicSubtractFunction() {
            assertEquals(0, calculator.subtract(1, 1));
        }

        @Test
        @DisplayName("Basic Subtract Functionality-2")
        void testSubtractFunction() {
            assertNotEquals(1, calculator.subtract(1, 1));
        }
    }

    @Nested
    @DisplayName("Multiplication Related Tests")
    class MultiplicationTests {
        @DisplayName("Multiplication Test via CSV file")
        @ParameterizedTest(name = "{index} -> number1={0}, number2={1}, result={2}")
        @CsvFileSource(resources = "/multiplication-data-source.csv", numLinesToSkip = 1)
        void testMultiplicationViaCSV(int a, int b, int result) {
            assertEquals(result, calculator.multiple(a, b));
        }
    }

    @Nested
    @DisplayName("Factorial Related Tests")
    class FactorialTests {
        @Test
        @DisplayName("Simple Factorial Test")
        void testFactorial() {
            assertEquals(362880, calculator.factorial(9));
        }

        @Disabled
        @Test
        @DisplayName("Simple Factorial Test-2")
        void testFactorial2() {
            assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
                Thread.sleep(101);
                calculator.factorial(5);
            });
        }
    }

    @ParameterizedTest
    @DisplayName("Test Division via MethodSource")
    @MethodSource("divisionProvider")
    void divisionViaMethodSource(int a, int b, double result) {
        assertEquals(result, calculator.divide(a, b));
    }

    private static Stream<Arguments> divisionProvider() {
        return Stream.of(Arguments.of(6, 3, 2.0),
                Arguments.of(30, 4, 7.5));
    }
}
