package com.company.calculator.service;

import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.TrigCalculationService;
import com.company.calculator.service.parser.ExpressionParser;
import com.company.calculator.service.parser.TrigFunctionParser;

public class TrigCalculatorFactory implements CalculatorFactory<PrefixExpressionValue> {

    @Override
    public ExpressionParser<PrefixExpressionValue> createParser() {
        return new TrigFunctionParser();
    }

    @Override
    public CalculationService<PrefixExpressionValue> createService() {
        return new TrigCalculationService();
    }

}
