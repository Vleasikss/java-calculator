package com.company.calculator.service.parser;

import com.company.calculator.model.expression.ExpressionValue;

public interface ExpressionParser<T extends ExpressionValue> {

    T parse(String expression);

}
