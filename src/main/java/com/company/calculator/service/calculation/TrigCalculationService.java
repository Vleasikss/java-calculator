package com.company.calculator.service.calculation;

import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.PrefixExpressionValue;
import scala.Function1;

import java.util.Map;

public class TrigCalculationService implements CalculationService<PrefixExpressionValue> {

    private static final Map<TrigFunction, Function1<Double, Double>> TRIG_FUNCTIONS = Map.of(
            TrigFunction.SIN, Math::sin,
            TrigFunction.COS, Math::cos,
            TrigFunction.TAN, Math::tan,
            TrigFunction.COT, (someValue) -> 1 / Math.tan(someValue)
    );

    @Override
    public double calculate(PrefixExpressionValue expressionValue) {
        try {
            String functionName = expressionValue.getExpression().name().toUpperCase();
            TrigFunction trigFunction = TrigFunction.valueOf(functionName);
            double result = TRIG_FUNCTIONS.get(trigFunction).apply(expressionValue.getValue());
            System.out.println(expressionValue.description() + "=" + result);
            return result;
        } catch (ArithmeticException e) {
            System.out.println("Data entered incorrectly");
            return 0.0;
        }
    }

}
