package com.company.calculator.model.expression;

import com.company.calculator.model.MathFunction;

/**
 * Marker interface
 */
public interface ExpressionValue {

    MathFunction getExpression();

    String description();

}
