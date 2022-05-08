package com.company.calculator.service;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.service.calculation.ArithmeticCalculationService;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.TrigCalculationService;
import org.junit.Test;

public class CalculatorServiceTest {

    private static final CalculationService<PrefixExpressionValue> service = new TrigCalculationService();


    @Test
    public void shouldCalculateTrigFunctionCorrectly() {
        PrefixExpressionValue cosExpression = new PrefixExpressionValue(TrigFunction.COS, 30);
        PrefixExpressionValue sinExpression = new PrefixExpressionValue(TrigFunction.SIN, 45);
        PrefixExpressionValue tanExpression = new PrefixExpressionValue(TrigFunction.TAN, 90);
        PrefixExpressionValue cotExpression = new PrefixExpressionValue(TrigFunction.COT, 60);

        double cosValue = service.calculate(cosExpression);
        assert cosValue == 0.15425144988758405;

        double sinValue = service.calculate(sinExpression);
        assert sinValue == 0.8509035245341184;

        double tanValue = service.calculate(tanExpression);
        assert tanValue == -1.995200412208242;

        double cotValue = service.calculate(cotExpression);
        assert cotValue == 3.124605622242308;

    }

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

}
