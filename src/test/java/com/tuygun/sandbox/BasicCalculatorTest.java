package com.tuygun.sandbox;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@EnabledOnJre(JRE.JAVA_8)
@DisplayName("Calculator Test - \uD83D\uDE42")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BasicCalculatorTest {

    @BeforeAll
    static void beforeAll() {
        //System.out.println("Before All");
    }

    @AfterAll
    static void afterAll() {
        //System.out.println("After All");
    }

    private static Stream<Arguments> divisionProvider() {
        return Stream.of(Arguments.of(6, 3, 2.0),
                Arguments.of(30, 4, 7.5));
    }

    @BeforeEach
    void beforeEach() {
        //System.out.println("Before Each");
    }

    @AfterEach
    void afterEach() {
        //System.out.println("After Each");
    }

    @Tag("SelfTest")
    @Order(1)
    @ParameterizedTest(name = "{index} -> message=''{0}''")
    @ValueSource(strings = {"test1", "test2"})
    void testStr(String message) {
        assertNotNull(message);
    }

    @Test
    @Order(2)
    @Tag("SelfTest")
    @EnabledIf(value = {
            "load('nashorn:mozilla_compat.js')",
            "importPackage(java.time)",
            "",
            "var today = LocalDate.now()",
            "var tomorrow = today.plusDays(1)",
            "tomorrow.isAfter(today)"
    },
            engine = "nashorn",
            reason = "Self-fulfilling: {result}")
    void simpleEnableTest(TestInfo testInfo) {
        assertAll(
                () -> assertTrue(true),
                () -> assertTrue(testInfo.getTags().contains("SelfTest")));
    }

    @ParameterizedTest
    @DisplayName("Test Division via MethodSource")
    @MethodSource("divisionProvider")
    void divisionViaMethodSource(int a, int b, double result) {
        assertEquals(result, BasicCalculator.divide(a, b));
    }

    @Nested
    @DisplayName("Addition Related Tests")
    @ExtendWith(RandomParametersExtension.class)
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

        @RepeatedTest(10)
        void testWithRandomInteger(@RandomParametersExtension.Random int i,
                                   @RandomParametersExtension.Random int j,
                                   RepetitionInfo repetitionInfo) {
            Assertions.assertAll(
                    () -> assertEquals(i + j, BasicCalculator.add(i, j)),
                    () -> assertTrue(repetitionInfo.getTotalRepetitions() == 10));
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
        @DisplayName("Simple Factorial Test On Dev Machine")
        @EnabledIfSystemProperty(named = "onDevMachine", matches = "true")
        void testFactorial() {
            assertThat(6L, is(BasicCalculator.factorial(3)));
        }

        @Test
        @DisplayName("Simple Factorial Test on Test Machine")
        void testFactorialOnTestEnv() {
            Assumptions.assumeTrue("false".equals(System.getProperty("onDevMachine")));
            assertThat(362880L, is(BasicCalculator.factorial(9)));
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

    @TestFactory
    @DisplayName("Basic Calculator Dynamic Tests")
    Collection<DynamicTest> dynamicTestViaCollections() {
        return Arrays.asList(
                DynamicTest.dynamicTest("Add Test",
                        () -> assertEquals(2, BasicCalculator.add(1, 1))),
                DynamicTest.dynamicTest("Subtract Test",
                        () -> assertEquals(0, BasicCalculator.subtract(2, 2))),
                DynamicTest.dynamicTest("Multiply Test",
                        () -> assertEquals(4, BasicCalculator.multiple(2, 2))),
                DynamicTest.dynamicTest("Division Test",
                        () -> assertEquals(2.0, BasicCalculator.divide(4, 2))),
                DynamicTest.dynamicTest("Factorial Test",
                        () -> assertEquals(24, BasicCalculator.factorial(4))));
    }
}
