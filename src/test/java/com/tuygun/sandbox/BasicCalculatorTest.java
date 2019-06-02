package com.tuygun.sandbox;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Test")
class BasicCalculatorTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After All");
    }

    private static Stream<Arguments> divisionProvider() {
        return Stream.of(Arguments.of(6, 3, 2.0),
                Arguments.of(30, 4, 7.5));
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

    @ParameterizedTest
    @DisplayName("Test Division via MethodSource")
    @MethodSource("divisionProvider")
    void divisionViaMethodSource(int a, int b, double result) {
        assertEquals(result, BasicCalculator.divide(a, b));
    }

    @Nested
    @DisplayName("Addition Related Tests")
    class AdditionTests {
        @Test
        @DisplayName("Check Calculator Add Functionality")
        void testAddFunction() {
            assertEquals(2,
                    BasicCalculator.add(1, 1), "Add functionality is not working");
        }

        @DisplayName("Addition Functionality Parameterized Test")
        @ParameterizedTest(name = "{index} -> number1={0}, number2={1}, result={2}")
        @CsvSource({"1, 1, 2", "2, 3, 5"})
        void testAddFunctionParameterized(int a, int b, int result) {
            assertEquals(result, BasicCalculator.add(a, b));
        }

        @Test
        @DisplayName("Integer Overflow")
        void integerOverFlow() {
            assertThrows(ArithmeticException.class, () -> BasicCalculator.add(Integer.MAX_VALUE, 1));
        }

        @DisplayName("Test Addition Functionality Via Converter")
        @ParameterizedTest(name = "{index} -> operation={0}")
        @ValueSource(strings = {"1+2=3", "23+43=66"})
        void testAdditionViaConverter(@ConvertWith(MathematicalOperationConverter.class)
                                              MathematicalOperation mathematicalOperation) {
            Assertions.assertAll(
                    () -> assertTrue(mathematicalOperation.getOperation().equals(MathematicalOperation.Operation.ADD)),
                    () -> assertEquals(mathematicalOperation.getResult(),
                            BasicCalculator.add(mathematicalOperation.getA(), mathematicalOperation.getB())));
        }
    }

    @Nested
    @DisplayName("Subtraction Related Tests")
    class SubtractTests {
        @Test
        @DisplayName("Basic Subtract Functionality-1")
        void testBasicSubtractFunction() {
            assertEquals(0, BasicCalculator.subtract(1, 1));
        }

        @Test
        @DisplayName("Basic Subtract Functionality-2")
        void testSubtractFunction() {
            assertNotEquals(1, BasicCalculator.subtract(1, 1));
        }

        @DisplayName("Subtract Test via External Argument Provider")
        @ParameterizedTest(name = "{index} -> number1={0}, number2={1}, result={2}")
        @ArgumentsSource(SubtractArgumentProvider.class)
        void testSubtractViaExternalArgumentProvider(int a, int b, int result) {
            assertEquals(result, BasicCalculator.subtract(a, b));
        }
    }

    @Nested
    @DisplayName("Multiplication Related Tests")
    class MultiplicationTests {
        @DisplayName("Multiplication Test via CSV file")
        @ParameterizedTest(name = "{index} -> number1={0}, number2={1}, result={2}")
        @CsvFileSource(resources = "/multiplication-data-source.csv", numLinesToSkip = 1)
        void testMultiplicationViaCSV(int a, int b, int result) {
            assertEquals(result, BasicCalculator.multiple(a, b));
        }

        @DisplayName("Test Multiplication Functionality Via Converter")
        @ParameterizedTest(name = "{index} -> operation={0}")
        @ValueSource(strings = {"1*2=2", "2*43=86"})
        void testAdditionViaConverter(@ConvertWith(MathematicalOperationConverter.class)
                                              MathematicalOperation mathematicalOperation) {
            Assertions.assertAll(
                    () -> assertTrue(mathematicalOperation.getOperation().equals(MathematicalOperation.Operation.MULTIPLY)),
                    () -> assertEquals(mathematicalOperation.getResult(),
                            BasicCalculator.multiple(mathematicalOperation.getA(), mathematicalOperation.getB())));
        }
    }

    @Nested
    @DisplayName("Factorial Related Tests")
    class FactorialTests {
        @Test
        @DisplayName("Simple Factorial Test")
        void testFactorial() {
            assertEquals(362880, BasicCalculator.factorial(9));
        }

        @Disabled
        @Test
        @DisplayName("Simple Factorial Test-2")
        void testFactorial2() {
            assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
                Thread.sleep(101);
                BasicCalculator.factorial(5);
            });
        }

        @DisplayName("Test Factorial Functionality Via Converter")
        @ParameterizedTest(name = "{index} -> operation={0}")
        @ValueSource(strings = {"3!=6", "0!=1"})
        void testAdditionViaConverter(@ConvertWith(MathematicalOperationConverter.class)
                                              MathematicalOperation mathematicalOperation) {
            Assertions.assertAll(
                    () -> assertTrue(mathematicalOperation.getOperation().equals(MathematicalOperation.Operation.FACTORIAL)),
                    () -> assertEquals(mathematicalOperation.getResult(),
                            BasicCalculator.factorial(mathematicalOperation.getA())));
        }
    }
}
