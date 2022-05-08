package com.company.calculator.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes(
        {
                @JsonSubTypes.Type(value = ArithmeticFunction.class, name = "arithmetic"),
                @JsonSubTypes.Type(value = TrigFunction.class, name = "trig")
        }
)
public interface MathFunction {

    String name();

    String getValue();

}
