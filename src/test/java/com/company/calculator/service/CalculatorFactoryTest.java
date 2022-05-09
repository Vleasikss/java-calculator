package com.company.calculator.service;

import com.company.calculator.model.expression.ExpressionValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CalculatorFactoryTest {

    @Test
    public void shouldDefineArithmeticFactory() {
        CalculatorFactory<ExpressionValue> factory = CalculatorFactory.createFactory("5 + 5");
        assertNotNull(factory);
        assertEquals(factory.getClass(), ArithmeticCalculationFactory.class);
    }

    @Test
    public void shouldDefineTrigFactory() {
        CalculatorFactory<ExpressionValue> factory = CalculatorFactory.createFactory("sin(30)");
        assertNotNull(factory);
        assertEquals(factory.getClass(), TrigCalculatorFactory.class);
    }

    @Test
    public void shouldDefineNull() {
        CalculatorFactory<ExpressionValue> factory = CalculatorFactory.createFactory("some invalid expression");
        assertNull(factory);
    }
}
