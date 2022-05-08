package com.company.calculator.service.parser;

import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticFunctionParser implements ExpressionParser<InfixExpressionValue> {

    private static final Pattern PARSE_ARITHMETIC_FUNCTION_PATTERN = Pattern.compile(buildParseArithmeticFunctionRegex(), Pattern.CASE_INSENSITIVE);
    //language=regexp
    private static final String MAYBE_SPACE_REGEX = "\\s?";

    @Override
    public InfixExpressionValue parse(String expression) {
        Matcher matcher = PARSE_ARITHMETIC_FUNCTION_PATTERN.matcher(expression);

        if (matcher.find()) {
            return parseOne(expression);
        }
        return null;
    }


    private static InfixExpressionValue parseOne(String expression) {
        ArithmeticFunction function = Arrays.stream(ArithmeticFunction.values())
                .filter(af -> expression.contains(af.getValue()))
                .findFirst()
                .get();

        String[] values = expression.split("\\" + function.getValue());
        double value1 = Double.parseDouble(values[0]);
        double value2 = Double.parseDouble(values[1]);

        return new InfixExpressionValue(function, value1, value2);
    }

    private static String buildParseArithmeticFunctionRegex() {
        StringBuilder builder = new StringBuilder();
        for (ArithmeticFunction function : ArithmeticFunction.values()) {
            builder.append("(");
            //language=regexp
            builder.append("-?\\d+");
            builder.append(MAYBE_SPACE_REGEX);
            builder.append("\\").append(function.getValue());
            builder.append(MAYBE_SPACE_REGEX);
            //language=regexp
            builder.append("-?\\d+");
            builder.append(")");
            builder.append("|");
        }

        String s = builder.toString();
        return s.substring(0, s.length() - 1);
    }

}
