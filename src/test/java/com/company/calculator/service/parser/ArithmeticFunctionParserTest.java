package com.company.calculator.service.parser;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ArithmeticFunctionParserTest {

    private static final ArithmeticFunctionParser parser = new ArithmeticFunctionParser();

    @Test
    public void shouldParseMinusExpressionCorrectly() {
        InfixExpressionValue parse1 = parser.parse("(-123) - (-123)");
        assertEquals(parse1, new InfixExpressionValue(ArithmeticFunction.MINUS, -123, -123));

        InfixExpressionValue parse2 = parser.parse("(-123) - 123");
        assertEquals(parse2, new InfixExpressionValue(ArithmeticFunction.MINUS, -123, 123));

        InfixExpressionValue parse3 = parser.parse("123 - 123");
        assertEquals(parse3, new InfixExpressionValue(ArithmeticFunction.MINUS, 123, 123));

        InfixExpressionValue parse4 = parser.parse("123 - (-123)");
        assertEquals(parse4, new InfixExpressionValue(ArithmeticFunction.MINUS, 123, -123));

    }

    @Test
    public void shouldParsePlusExpressionCorrectly() {
        InfixExpressionValue parse1 = parser.parse("(-123) + (-123)");
        assertEquals(parse1, new InfixExpressionValue(ArithmeticFunction.PLUS, -123, -123));

        InfixExpressionValue parse2 = parser.parse("(-123) + 123");
        assertEquals(parse2, new InfixExpressionValue(ArithmeticFunction.PLUS, -123, 123));

        InfixExpressionValue parse3 = parser.parse("123 + 123");
        assertEquals(parse3, new InfixExpressionValue(ArithmeticFunction.PLUS, 123, 123));

        InfixExpressionValue parse4 = parser.parse("123 + (-123)");
        assertEquals(parse4, new InfixExpressionValue(ArithmeticFunction.PLUS, 123, -123));

    }


    @Test
    public void shouldParseMultiplyExpressionCorrectly() {
        InfixExpressionValue parse1 = parser.parse("(-123) * (-123)");
        assertEquals(parse1, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, -123, -123));

        InfixExpressionValue parse2 = parser.parse("(-123) * 123");
        assertEquals(parse2, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, -123, 123));

        InfixExpressionValue parse3 = parser.parse("123 * 123");
        assertEquals(parse3, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, 123, 123));

        InfixExpressionValue parse4 = parser.parse("123 * (-123)");
        assertEquals(parse4, new InfixExpressionValue(ArithmeticFunction.MULTIPLY, 123, -123));

    }


    @Test
    public void shouldParseDivideExpressionCorrectly() {
        InfixExpressionValue parse1 = parser.parse("(-123) / (-123)");
        assertEquals(parse1, new InfixExpressionValue(ArithmeticFunction.DIVIDE, -123, -123));

        InfixExpressionValue parse2 = parser.parse("(-123) / 123");
        assertEquals(parse2, new InfixExpressionValue(ArithmeticFunction.DIVIDE, -123, 123));

        InfixExpressionValue parse3 = parser.parse("123 / 123");
        assertEquals(parse3, new InfixExpressionValue(ArithmeticFunction.DIVIDE, 123, 123));

        InfixExpressionValue parse4 = parser.parse("123 / (-123)");
        assertEquals(parse4, new InfixExpressionValue(ArithmeticFunction.DIVIDE, 123, -123));

    }

    @Test
    public void shouldNotParseIncorrectExpression() {
        InfixExpressionValue expressionValue = parser.parse("123 23 S+ 3");
        assertNull(expressionValue);
    }

}
