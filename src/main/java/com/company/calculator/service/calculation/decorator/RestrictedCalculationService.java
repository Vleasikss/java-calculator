package com.company.calculator.service.calculation.decorator;

import com.company.calculator.model.expression.ExpressionValue;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.TrigCalculationService;

import java.util.Optional;

public class RestrictedCalculationService<T extends ExpressionValue> implements CalculationService<T> {

    private final CalculationService<T> calculationService;
    private final Optional<String> userId;

    public RestrictedCalculationService(CalculationService<T> calculationService, Optional<String> userId) {
        this.calculationService = calculationService;
        this.userId = userId;
    }

    @Override
    public double calculate(T expressionValue) {
        if (calculationService instanceof TrigCalculationService) {
            if (userId.isPresent()) {
                return calculationService.calculate(expressionValue);
            }
            throw new UnsupportedOperationException();
        }
        return calculationService.calculate(expressionValue);
    }
}
