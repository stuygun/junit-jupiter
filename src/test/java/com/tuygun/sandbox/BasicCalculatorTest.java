package com.tuygun.sandbox;

import com.tuygun.sandbox.BasicCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicCalculatorTest {
    BasicCalculator calculator;

    @BeforeEach
    public void init(){
        calculator = new BasicCalculator();
    }

    @Test
    void testAddFunction(){
        assertEquals(2.0,
                calculator.add(1.0, 1.0), "Add functionality is not working");
    }
}
