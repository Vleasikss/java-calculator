package com.company.calculator.service.calculation;

import com.company.calculator.exception.InvalidDataException;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.PrefixExpressionValue;
import org.junit.Test;

import static org.junit.Assert.assertThrows;

public class TrigCalculationServiceTest {

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

    @Test
    public void shouldNotCalculateTrigFunctionCorrectly() {
        PrefixExpressionValue cosExpression = new PrefixExpressionValue(null, 30);

        assertThrows(InvalidDataException.class, () -> service.calculate(cosExpression));

    }

}
