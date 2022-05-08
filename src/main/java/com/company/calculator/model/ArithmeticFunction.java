package com.company.calculator.model;

public enum ArithmeticFunction implements MathFunction {
    DIVIDE("/"), MULTIPLY("*"), MINUS("-"), PLUS("+");

    private final String value;

    ArithmeticFunction(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public static ArithmeticFunction ofValue(String value) {
        if (value.equals(DIVIDE.value)) {
            return DIVIDE;
        }
        if (value.equals(MULTIPLY.value)) {
            return MULTIPLY;
        }
        if (value.equals(MINUS.value)) {
            return MINUS;
        }
        if (value.equals(PLUS.value)) {
            return PLUS;
        }
        return null;
    }

}
