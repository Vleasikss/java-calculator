package com.company.calculator.model.expression;

import com.company.calculator.model.MathFunction;

import java.util.Objects;

/**
 * Expression is in center, operands are left and right
 */
public class InfixExpressionValue implements ExpressionValue {

    private final MathFunction expression;
    private final double leftValue;
    private final double rightValue;

    public InfixExpressionValue(MathFunction expression, double leftValue, double rightValue) {
        this.expression = expression;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public MathFunction getExpression() {
        return expression;
    }

    @Override
    public String description() {
        return leftValue + expression.getValue() + rightValue;
    }

    public double getLeftValue() {
        return leftValue;
    }

    public double getRightValue() {
        return rightValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfixExpressionValue that = (InfixExpressionValue) o;
        return Double.compare(that.leftValue, leftValue) == 0 && Double.compare(that.rightValue, rightValue) == 0 && Objects.equals(expression, that.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, leftValue, rightValue);
    }

    @Override
    public String toString() {
        return "InfixExpressionValue{" +
                "expression=" + expression +
                ", leftValue=" + leftValue +
                ", rightValue=" + rightValue +
                '}';
    }
}
