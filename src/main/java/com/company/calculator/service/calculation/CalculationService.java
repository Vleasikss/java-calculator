package com.company.calculator.service.calculation;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.ExpressionValue;
import com.company.calculator.model.expression.InfixExpressionValue;
import com.company.calculator.model.expression.PrefixExpressionValue;
import com.company.calculator.model.TrigFunction;
import scala.Function1;
import scala.Function2;

import java.util.Map;

public interface CalculationService<T extends ExpressionValue> {


    double calculate(T expressionValue);


}
