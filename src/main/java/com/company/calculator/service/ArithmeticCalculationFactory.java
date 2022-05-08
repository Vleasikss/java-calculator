package com.company.calculator.service;

import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.service.calculation.ArithmeticCalculationService;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.parser.ArithmeticFunctionParser;
import com.company.calculator.service.parser.ExpressionParser;

public class ArithmeticCalculationFactory implements CalculatorFactory<InfixExpressionValue> {

    @Override
    public ExpressionParser<InfixExpressionValue> createParser() {
        return new ArithmeticFunctionParser();
    }

    @Override
    public CalculationService<InfixExpressionValue> createService() {
        return new ArithmeticCalculationService();
    }

}
