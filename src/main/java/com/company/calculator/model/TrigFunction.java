package com.company.calculator.model;

public enum TrigFunction implements MathFunction {
    SIN, COS, TAN, COT;

    @Override
    public String getValue() {
        return name();
    }
}
