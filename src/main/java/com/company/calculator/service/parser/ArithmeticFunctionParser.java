package com.company.calculator.service.parser;

import com.company.calculator.exception.InvalidDataException;
import com.company.calculator.model.ArithmeticFunction;
import com.company.calculator.model.expression.InfixExpressionValue;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArithmeticFunctionParser implements ExpressionParser<InfixExpressionValue> {

    private static final String EXTRACT_BRACKETS_REGEX = "[\\[\\]()]";
    private static final Pattern PARSE_ARITHMETIC_FUNCTION_PATTERN = Pattern.compile(buildParseArithmeticFunctionRegex(), Pattern.CASE_INSENSITIVE);
    private static final Pattern PARSE_NUMBER_PATTERN = Pattern.compile("-?\\d+");

    @Override
    public InfixExpressionValue parse(String expression) {
        try {
            String matchWithoutBrackets = expression.replaceAll(EXTRACT_BRACKETS_REGEX, "");
            Matcher matcher = PARSE_ARITHMETIC_FUNCTION_PATTERN.matcher(matchWithoutBrackets);

            if (matcher.find()) {
                return parseOne(expression);
            }

            throw new InvalidDataException();
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }


    private static InfixExpressionValue parseOne(String expression) {
        ArithmeticFunction function = Arrays.stream(ArithmeticFunction.values())
                .filter(af -> expression.contains(af.getValue()))
                .findFirst()
                .orElse(ArithmeticFunction.PLUS);

        Matcher numberMatch = PARSE_NUMBER_PATTERN.matcher(expression);

        double[] values = new double[2];

        for (int i = 0; i < values.length; i++) {
            if (numberMatch.find()) {
                values[i] = Double.parseDouble(numberMatch.group());
            }
        }

        return new InfixExpressionValue(function, values[0], values[1]);
    }

    /**
     * @return (- ? \ d + \)?\s?\/\s?-?\(?\d+)|(-?\d+\)?\s?\*\s?-?\(?\d+)|(-?\d+\)?\s?\+\s?-?\(?\d+)|(-?\d+\)?\s?\-\s?-?\(?\d+)
     */
    private static String buildParseArithmeticFunctionRegex() {
        StringBuilder builder = new StringBuilder();
        for (ArithmeticFunction function : ArithmeticFunction.values()) {
            //language=regexp
            builder.append("(-?\\d+\\)?\\s?\\");
            builder.append(function.getValue());
            //language=regexp
            builder.append("\\s?-?\\(?\\d+)|");
        }

        String s = builder.toString();
        return s.substring(0, s.length() - 1);
    }

}
