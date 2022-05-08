package com.company.calculator.service;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.ExpressionValue;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.parser.ExpressionParser;

public interface CalculatorFactory<T extends ExpressionValue> {

    ExpressionParser<T> createParser();

    CalculationService<T> createService();

    @SuppressWarnings("unchecked")
    static<T extends ExpressionValue> CalculatorFactory<T> createFactory(String expression) {
        for (TrigFunction function : TrigFunction.values()) {
            if (containsIgnoreCase(expression, function.getValue())) {
                return (CalculatorFactory<T>) new TrigCalculatorFactory();
            }
        }
        for (ArithmeticFunction function : ArithmeticFunction.values()) {
            if (containsIgnoreCase(expression, function.getValue())) {
                return (CalculatorFactory<T>) new ArithmeticCalculationFactory();
            }
        }

        return null;
    }

    private static boolean containsIgnoreCase(String str1, String str2) {
        return str1.toLowerCase().contains(str2.toLowerCase());
    }

}
