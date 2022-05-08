package com.company.calculator.service.calculation.decorator;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.service.calculation.ArithmeticCalculationService;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.TrigCalculationService;
import org.junit.Test;

import java.util.Optional;

public class RestrictedCalculationServiceTest {


    @Test
    public void shouldRestrictAccessOnUndefinedUser() {
        CalculationService<PrefixExpressionValue> service = new RestrictedCalculationService<>(new TrigCalculationService(), Optional.empty());
        PrefixExpressionValue sinExpression = new PrefixExpressionValue(TrigFunction.SIN, 45);

        double result = service.calculate(sinExpression);
        assert result == 0.0;
    }

    @Test
    public void shouldGiveAccessOnDefinedUser() {
        CalculationService<PrefixExpressionValue> service = new RestrictedCalculationService<>(new TrigCalculationService(), Optional.of("1"));
        PrefixExpressionValue sinExpression = new PrefixExpressionValue(TrigFunction.SIN, 30);

        double result = service.calculate(sinExpression);
        assert result == -0.9880316240928618;
    }

    @Test
    public void shouldGiveAccessOnUndefinedUser() {
        CalculationService<InfixExpressionValue> service = new RestrictedCalculationService<>(new ArithmeticCalculationService(), Optional.empty());
        InfixExpressionValue minusExpression = new InfixExpressionValue(ArithmeticFunction.MINUS, 5, 5);

        double result = service.calculate(minusExpression);
        assert result == 0;
    }

    @Test
    public void shouldGiveAccessArithmeticOnDefinedUser() {
        CalculationService<InfixExpressionValue> service = new RestrictedCalculationService<>(new ArithmeticCalculationService(), Optional.of("1"));
        InfixExpressionValue minusExpression = new InfixExpressionValue(ArithmeticFunction.MINUS, 5, 5);

        double result = service.calculate(minusExpression);
        assert result == 0;
    }
}
