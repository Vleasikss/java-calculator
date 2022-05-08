package com.company.calculator.service.parser;

import com.company.calculator.model.MathFunction;
import com.company.calculator.model.TrigFunction;
import com.company.calculator.model.expression.PrefixExpressionValue;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrigFunctionParser implements ExpressionParser<PrefixExpressionValue> {

    private static final String EXTRACT_BRACKETS_REGEX = "[\\[\\]()]";
    private static final String EXTRACT_ALL_BEFORE_FIRST_BRACKET_REGEX = "\\(";
    private static final String EXTRACT_VALUE_FROM_TRIG_FUNCTION_REGEX = "\\(\\d+\\)";

    private static final Pattern EXTRACT_VALUE_FROM_TRIG_FUNCTION_PATTERN = Pattern.compile(EXTRACT_VALUE_FROM_TRIG_FUNCTION_REGEX);
    private static final Pattern PARSE_TRIG_FUNCTION_PATTERN = Pattern.compile(buildParseTrigFunctionRegex(), Pattern.CASE_INSENSITIVE);

    @Override
    public PrefixExpressionValue parse(String expression) {
        Matcher matcher = PARSE_TRIG_FUNCTION_PATTERN.matcher(expression);

        if (matcher.find()) {
            return parseOne(matcher.group()).orElse(null);
        }

        return null;
    }

    private static Optional<PrefixExpressionValue> parseOne(String group) {
        Matcher extractValueFromTrigFunctionMatcher = EXTRACT_VALUE_FROM_TRIG_FUNCTION_PATTERN.matcher(group);
        if (extractValueFromTrigFunctionMatcher.find()) {
            String strValue = extractValueFromTrigFunctionMatcher.group().replaceAll(EXTRACT_BRACKETS_REGEX,"");
            double value = Double.parseDouble(strValue);

            String strFunction = group.split(EXTRACT_ALL_BEFORE_FIRST_BRACKET_REGEX)[0].toUpperCase();
            MathFunction mathFunction = TrigFunction.valueOf(strFunction);

            return Optional.of(new PrefixExpressionValue(mathFunction, value));
        }
        return Optional.empty();
    }

    private static String buildParseTrigFunctionRegex() {
        StringBuilder builder = new StringBuilder();
        for (TrigFunction function : TrigFunction.values()) {
            builder.append("(");
            builder.append(function.name().toLowerCase(Locale.ROOT));
            builder.append(EXTRACT_VALUE_FROM_TRIG_FUNCTION_REGEX);
            builder.append(")");
            builder.append("|");
        }

        String s = builder.toString();
        return s.substring(0, s.length() - 1);
    }

}
