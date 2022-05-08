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

}
