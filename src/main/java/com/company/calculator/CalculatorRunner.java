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

public final class CalculatorRunner {

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\n\\s*");
    private static final PrinterService historyLogPrinterService = new HistoryLogPrinterService();

    public static void run(Optional<String> userId) {
        CalculatorFactory<ExpressionValue> calculatorFactory;
        RestrictedHistoryStorageHelper historyStorageHelper = new RestrictedHistoryStorageHelper(new HistoryStorageHelper(), userId);
        PrinterService calculatorLogPrinterService = new CalculatorLogPrinterService(historyStorageHelper);

        while (true) {
            System.out.println("\nEnter your expression: ");
            String expression = scanner.next();

            if (expression.equals(KeyExpressions.EXIT)) {
                System.exit(0);
            } else if (expression.equals(KeyExpressions.HISTORY)) {
                historyLogPrinterService.print(userId.orElse(null));
            } else if ((calculatorFactory = CalculatorFactory.createFactory(expression)) != null) {
                calculate(userId, calculatorFactory, historyStorageHelper, calculatorLogPrinterService, expression);
            } else {
                System.out.println("Some unexpected expression");
            }
        }
    }

    private static void calculate(
            Optional<String> userId,
            CalculatorFactory<ExpressionValue> calculatorFactory,
            RestrictedHistoryStorageHelper historyStorageHelper,
            PrinterService calculatorLogPrinterService,
            String expression
    ) {
        CalculationService<ExpressionValue> calculationService = new RestrictedCalculationService<>(calculatorFactory.createService(), userId);
        ExpressionParser<ExpressionValue> expressionParser = calculatorFactory.createParser();

        try {
            ExpressionValue expressionValue = expressionParser.parse(expression);
            double result = calculationService.calculate(expressionValue);
            String description = HistoryAction.buildResultDescription(expressionValue.description(), result);

            HistoryAction historyAction = new HistoryAction(
                    expressionValue.getExpression(),
                    userId.orElse(null),
                    description
            );
            historyStorageHelper.save(historyAction);

            calculatorLogPrinterService.print(historyAction.getId());
        } catch (NoAccessException e) {
            System.out.println("You have no access to use such calculations");
        } catch (InvalidDataException e) {
            System.out.println("Invalid data provided");
        }
    }
}
