package com.company.calculator.service;

import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.service.parser.TrigFunctionParser;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TrigFunctionParserTest {

    private static final TrigFunctionParser trigFunctionParser = new TrigFunctionParser();


    @Test
    public void shouldParseTrigFunction() {
        PrefixExpressionValue expressionValues = trigFunctionParser.parse("sin(30)");
        assertEquals(expressionValues, new PrefixExpressionValue(TrigFunction.SIN, 30));

        PrefixExpressionValue expressionValues1 = trigFunctionParser.parse("cos(30)");
        assertEquals(expressionValues1, new PrefixExpressionValue(TrigFunction.COS, 30));

        PrefixExpressionValue expressionValues2 = trigFunctionParser.parse("cot(30)");
        assertEquals(expressionValues2, new PrefixExpressionValue(TrigFunction.COT, 30));

        PrefixExpressionValue expressionValues3 = trigFunctionParser.parse("tan(30)");
        assertEquals(expressionValues3, new PrefixExpressionValue(TrigFunction.TAN, 30));

    }

    @Test
    public void shouldParseTrigFunctionsExceptOne() {
        PrefixExpressionValue expressionValues = trigFunctionParser.parse("sin30)");
        assertNull(expressionValues);
    }

    @Test
    public void shouldParseNoTrigFunction() {
        PrefixExpressionValue expressionValues = trigFunctionParser.parse("sin30 cos45");
        assertNull(expressionValues);
    }

}
