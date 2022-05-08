package com.company.calculator;

import com.company.calculator.db.UserStorageHelper;
import com.company.calculator.model.User;
import com.company.calculator.service.auth.UserAuthorizer;

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
                CalculatorRunner.run(Optional.of(user.getId()));
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
                    CalculatorRunner.run(Optional.empty());
                }
            }

            if (Objects.equals(answer, KeyExpressions.EXIT)) {
                break;
            }
        }
    }
}
