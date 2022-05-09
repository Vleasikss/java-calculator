package com.company.calculator.service.calculation;

import com.company.calculator.exception.InvalidDataException;
import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class ArithmeticCalculationServiceTest {

    private static final CalculationService<InfixExpressionValue> service1 = new ArithmeticCalculationService();

    @Test
    public void shouldCalculateArithmeticFunctionsCorrectly() {
        InfixExpressionValue plusExpression = new InfixExpressionValue(ArithmeticFunction.PLUS, 5, 5);
        InfixExpressionValue minusExpression = new InfixExpressionValue(ArithmeticFunction.MINUS, 5, 5);
        InfixExpressionValue multiplyExpression = new InfixExpressionValue(ArithmeticFunction.MULTIPLY, 5, 5);
        InfixExpressionValue divideExpression = new InfixExpressionValue(ArithmeticFunction.DIVIDE, 5, 5);

        double plusValue = service1.calculate(plusExpression);
        assert plusValue == 10;

        double minusValue = service1.calculate(minusExpression);
        assert minusValue == 0;

        double multiplyValue = service1.calculate(multiplyExpression);
        assert multiplyValue == 25;

        double divideValue = service1.calculate(divideExpression);
        assert divideValue == 1;

    }

    @Test
    public void shouldNotCalculateArithmeticFunctionsCorrectly() {
        InfixExpressionValue plusExpression = new InfixExpressionValue(null, 5, 5);
        assertThrows(InvalidDataException.class, () -> service1.calculate(plusExpression));
    }

}
