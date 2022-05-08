package com.company.calculator.service;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.service.parser.ArithmeticFunctionParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ArithmeticFunctionParserTest {

    private static final ArithmeticFunctionParser parser = new ArithmeticFunctionParser();


    @Test
    public void shouldParseArithmeticFunctionsCorrectly() {
        InfixExpressionValue parse = parser.parse("5 + 5");
        assertEquals(parse, new InfixExpressionValue(ArithmeticFunction.PLUS, 5, 5));

        InfixExpressionValue parse1 = parser.parse("5 * 5");
        assertEquals(parse1, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, 5, 5));

        InfixExpressionValue parse2 = parser.parse("5 / 5");
        assertEquals(parse2, new InfixExpressionValue(ArithmeticFunction.DIVIDE, 5, 5));

        InfixExpressionValue parse3 = parser.parse("5 - 5");
        assertEquals(parse3, new InfixExpressionValue(ArithmeticFunction.MINUS, 5, 5));

        InfixExpressionValue parse4 = parser.parse("20*-4");
        assertEquals(parse4, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, 20, -4));

        InfixExpressionValue parse5 = parser.parse("-20*-4");
        assertEquals(parse5, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, -20, -4));

        InfixExpressionValue parse6 = parser.parse("-20+-4");
        assertEquals(parse6, new InfixExpressionValue(ArithmeticFunction.PLUS, -20, -4));

        InfixExpressionValue parse7 = parser.parse("-40/-40");
        assertEquals(parse7, new InfixExpressionValue(ArithmeticFunction.DIVIDE, -40, -40));

//      todo   throws error
//        InfixExpressionValue parse6 = parser.parse("-20--4");
//        assertEquals(parse6, new InfixExpressionValue(ArithmeticFunction.MINUS, -20, -4));
    }

    @Test
    public void shouldNotParseIncorrectExpression() {
        InfixExpressionValue expressionValue = parser.parse("5 23 S+ 3");
        assertNull(expressionValue);
    }

}
