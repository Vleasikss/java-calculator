package com.company.calculator.service.parser;

import com.company.calculator.model.expression.ExpressionValue;

public interface ExpressionParser<T extends ExpressionValue> {

//    todo move to Optional
    T parse(String expression);

}
