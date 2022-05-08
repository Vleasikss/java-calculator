package com.company.calculator.service.calculation;

import com.company.calculator.model.expression.ExpressionValue;

public interface CalculationService<T extends ExpressionValue> {

    double calculate(T expressionValue);

}
