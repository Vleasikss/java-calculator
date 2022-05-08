package com.company.calculator;

import com.company.calculator.db.HistoryStorageHelper;
import com.company.calculator.db.RestrictedHistoryStorageHelper;
import com.company.calculator.db.UserStorageHelper;
import com.company.calculator.model.HistoryAction;
import com.company.calculator.model.User;
import com.company.calculator.model.expression.ExpressionValue;
import com.company.calculator.service.CalculatorFactory;
import com.company.calculator.service.auth.UserAuthorizer;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.decorator.RestrictedCalculationService;
import com.company.calculator.service.parser.ExpressionParser;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final UserStorageHelper userStorageHelper = new UserStorageHelper();
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n\\s*");

    public static void main(String[] args) {

        System.out.println("Dear User," +
                "If you are registered u can use operations such as '+', '-', '/', '*'(for example 22+3, 24/2) and functions" +
                "'sin', 'cos', 'tan', 'cot'(for example sin 23, cot 34). Otherwise, you can only use such operators as" +
                "'+', '-', '/', '*'."
        );

        UserAuthorizer userAuthorizer = new UserAuthorizer(userStorageHelper);
        while (true) {
            System.out.println("Are you registered?(yes/no)");
            String answer = scanner.next();
            if (Objects.equals(answer, KeyExpressions.YES)) {
                Optional<User> maybeUser = userAuthorizer.processAuthorization();
                if (maybeUser.isEmpty()) {
                    continue;
                }
                System.out.println("Enter q if you wanna finish.If you want to check your history, enter " + KeyExpressions.HISTORY + ".");
                User user = maybeUser.get();
                startCalculations(Optional.ofNullable(user.getId()));
            }
            if (Objects.equals(answer, KeyExpressions.NO)) {
                System.out.println("Do you want to reg?(yes/no) ");
                String authenticationAnswer = scanner.next();
                if (authenticationAnswer.equals(KeyExpressions.YES)) {
                    userAuthorizer.processAuthentication();
                }
                if (authenticationAnswer.equals(KeyExpressions.NO)) {
                    System.out.println("So, you can use only ('+', '-', '/', '*')");
                    System.out.println("\nEnter q if you wanna finish");
                    startCalculations(Optional.empty());
                }
            }
        }
    }


    private static void startCalculations(Optional<String> userId) {
        CalculatorFactory<ExpressionValue> calculatorFactory;
        RestrictedHistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new HistoryStorageHelper(), userId);

        while (true) {
            System.out.println("\nEnter your expression: ");
            String expression = scanner.next();

            if (expression.equals(KeyExpressions.EXIT)) {
                System.exit(0);
            } else if (expression.equals(KeyExpressions.HISTORY)) {
                List<HistoryAction> byUserId = historyStorageHelper.findByUserId(userId.orElse(null));
                System.out.println(byUserId);
            } else if ((calculatorFactory = CalculatorFactory.createFactory(expression)) != null) {
                CalculationService<ExpressionValue> calculationService =
                        new RestrictedCalculationService<>(calculatorFactory.createService(), userId);
                ExpressionParser<ExpressionValue> expressionParser = calculatorFactory.createParser();

                ExpressionValue expressionValue = expressionParser.parse(expression);
                double result = calculationService.calculate(expressionValue);
                String desc = HistoryAction.buildDescription(expression, result);

                HistoryAction historyAction = new HistoryAction(
                        expressionValue.getExpression(),
                        userId.orElse(null),
                        desc
                );
                historyStorageHelper.save(historyAction);
            } else {
                System.out.println("Some unexpected expression");
            }
        }
    }
}
