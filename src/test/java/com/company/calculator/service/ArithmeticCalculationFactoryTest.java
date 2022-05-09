package com.company.calculator.service;

import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.parser.ExpressionParser;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ArithmeticCalculationFactoryTest {

    @Test
    public void shouldCreateFactory() {
        ArithmeticCalculationFactory arithmeticCalculationFactory = new ArithmeticCalculationFactory();
        ExpressionParser<InfixExpressionValue> parser = arithmeticCalculationFactory.createParser();
        CalculationService<InfixExpressionValue> service = arithmeticCalculationFactory.createService();
        assertNotNull(parser);
        assertNotNull(service);
    }
}
