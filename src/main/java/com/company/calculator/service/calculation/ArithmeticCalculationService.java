package com.company.calculator.service.calculation;

import com.company.calculator.exception.InvalidDataException;
import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import scala.Function2;

import java.util.Map;

public class ArithmeticCalculationService implements CalculationService<InfixExpressionValue> {

    private static final Map<ArithmeticFunction, Function2<Double, Double, Double>> ARITHMETIC_FUNCTIONS = Map.of(
            ArithmeticFunction.PLUS, Double::sum,
            ArithmeticFunction.MINUS, (d1, d2) -> d1 - d2,
            ArithmeticFunction.MULTIPLY, (d1, d2) -> d1 * d2,
            ArithmeticFunction.DIVIDE, (d1, d2) -> d1 / d2
    );

    @Override
    public double calculate(InfixExpressionValue expressionValue) {
        try {
            String functionName = expressionValue.getExpression().name().toUpperCase();
            ArithmeticFunction arithmeticFunction = ArithmeticFunction.valueOf(functionName);
            return ARITHMETIC_FUNCTIONS.get(arithmeticFunction).apply(expressionValue.getLeftValue(), expressionValue.getRightValue());
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }
}
