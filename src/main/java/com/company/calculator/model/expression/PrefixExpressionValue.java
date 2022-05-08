package com.company.calculator.model.expression;

import com.company.calculator.model.MathFunction;

import java.util.Objects;

/**
 * Expression is left, value,number is right
 */
public class PrefixExpressionValue implements ExpressionValue {

    private final MathFunction expression;
    private final double value;

    public PrefixExpressionValue(MathFunction expression, double value) {
        this.expression = expression;
        this.value = value;
    }

    public MathFunction getExpression() {
        return expression;
    }

    @Override
    public String description() {
        return expression.getValue() + "(" + value + ")";
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrefixExpressionValue that = (PrefixExpressionValue) o;
        return Double.compare(that.value, value) == 0 && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, value);
    }



}
