package com.company.calculator;

import com.company.calculator.db.HistoryStorageHelper;
import com.company.calculator.db.RestrictedHistoryStorageHelper;
import com.company.calculator.exception.InvalidDataException;
import com.company.calculator.exception.NoAccessException;
import com.company.calculator.model.HistoryAction;
import com.company.calculator.model.expression.ExpressionValue;
import com.company.calculator.service.CalculatorFactory;
import com.company.calculator.service.calculation.CalculationService;
import com.company.calculator.service.calculation.decorator.RestrictedCalculationService;
import com.company.calculator.service.parser.ExpressionParser;
import com.company.calculator.service.printer.CalculatorLogPrinterService;
import com.company.calculator.service.printer.HistoryLogPrinterService;
import com.company.calculator.service.printer.PrinterService;

import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public final class CalculatorRunner {

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n\\s*");
    private static final PrinterService historyLogPrinterService = new HistoryLogPrinterService();

    public static void run(Optional<String> maybeUserId) {
        CalculatorFactory<ExpressionValue> calculatorFactory;
        RestrictedHistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new HistoryStorageHelper(), maybeUserId);
        PrinterService calculatorLogPrinterService = new CalculatorLogPrinterService(historyStorageHelper);

        while (true) {
            System.out.println("\nEnter your expression: ");
            String expression = scanner.next();

            if (expression.equals(KeyExpressions.EXIT)) {
                System.exit(0);
            } else if (expression.equals(KeyExpressions.HISTORY)) {
                logHistory(maybeUserId);
            } else if ((calculatorFactory = CalculatorFactory.createFactory(expression)) != null) {
                calculate(maybeUserId, calculatorFactory, historyStorageHelper, calculatorLogPrinterService, expression);
            } else {
                System.out.println("Some unexpected expression");
            }
        }
    }

    private static void logHistory(Optional<String> maybeUserId) {
        try {
            historyLogPrinterService.print(maybeUserId.orElse(null));
        } catch (NoAccessException e) {
            System.out.println("You have no access to use such operations");
        }
    }

    private static void calculate(
            Optional<String> maybeUserId,
            CalculatorFactory<ExpressionValue> calculatorFactory,
            RestrictedHistoryStorageHelper historyStorageHelper,
            PrinterService calculatorLogPrinterService,
            String expression
    ) {
        CalculationService<ExpressionValue> calculationService = new RestrictedCalculationService<>(calculatorFactory.createService(), maybeUserId);
        ExpressionParser<ExpressionValue> expressionParser = calculatorFactory.createParser();
        try {
            ExpressionValue expressionValue = expressionParser.parse(expression);
            double result = calculationService.calculate(expressionValue);

            HistoryAction historyAction = HistoryAction.newBuilder()
                    .withId(UUID.randomUUID().toString().substring(0, 6))
                    .withFunction(expressionValue.getExpression())
                    .withDesc(expression, result)
                    .withUserId(maybeUserId.orElse(null))
                    .build();

            historyStorageHelper.save(historyAction);
            calculatorLogPrinterService.print(historyAction.getId());
        } catch (NoAccessException e) {
            System.out.println("You have no access to use such calculations");
        } catch (InvalidDataException e) {
            System.out.println("Invalid data provided");
        }
    }
}
