package com.company.calculator.service;

import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.parser.ExpressionParser;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TrigCalculatorFactoryTest {

    @Test
    public void shouldCreateFactory() {
        TrigCalculatorFactory factory = new TrigCalculatorFactory();
        ExpressionParser<PrefixExpressionValue> parser = factory.createParser();
        CalculationService<PrefixExpressionValue> service = factory.createService();
        assertNotNull(parser);
        assertNotNull(service);
    }
}
