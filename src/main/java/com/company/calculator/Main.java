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
                User user = authorize(userAuthorizer);
                System.out.println("Enter q if you wanna finish.If you want to check your history, enter " + KeyExpressions.HISTORY + ".");
                CalculatorRunner.run(Optional.of(user.getId()));
            }

            if (Objects.equals(answer, KeyExpressions.NO)) {
                System.out.println("Do you want to reg?(yes/no) ");
                String authenticationAnswer = scanner.next();
                if (authenticationAnswer.equals(KeyExpressions.YES)) {
                    User user = auth(userAuthorizer);
                    CalculatorRunner.run(Optional.of(user.getId()));
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

    private static User auth(UserAuthorizer userAuthorizer) {
        System.out.println("Enter your login: ");
        String login = scanner.next();

        System.out.println("Enter your password: ");
        String password = scanner.next();

        System.out.println("Repeat your password: ");
        String repeatPassword = scanner.next();
        Optional<User> maybeUser = userAuthorizer.processAuthentication(login, password, repeatPassword);
        if (maybeUser.isEmpty()) {
            return auth(userAuthorizer);
        }
        return maybeUser.get();
    }

    private static User authorize(UserAuthorizer userAuthorizer) {
        System.out.println("Please, enter your password and login");

        System.out.println("Enter your login: ");
        String login = scanner.next();

        System.out.println("Enter your password: ");
        String password = scanner.next();

        Optional<User> maybeUser = userAuthorizer.processAuthorization(login, password);
        if (maybeUser.isEmpty()) {
            return authorize(userAuthorizer);
        }
        return maybeUser.get();
    }
}
