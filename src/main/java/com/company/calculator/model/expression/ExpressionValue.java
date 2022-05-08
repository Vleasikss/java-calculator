package com.company.calculator.model.expression;

import com.company.calculator.model.MathFunction;


public interface ExpressionValue {

    MathFunction getExpression();

    String description();

}
