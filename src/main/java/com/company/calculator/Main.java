package com.company.calculator;

import com.company.calculator.db.HistoryHelper;
import com.company.calculator.db.HistoryHelperImpl;
import com.company.calculator.db.UserHelper;
import com.company.calculator.db.UserHelperImpl;
import com.company.calculator.model.Action;
import com.company.calculator.model.HistoryAction;
import com.company.calculator.model.LoginUser;
import com.company.calculator.model.User;

import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String FILENAME_USERS = "person.json";
    private static final String USERS_HISTORY = "users_history.json";

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserHelper userHelper = new UserHelperImpl();

    public static void main(String[] args) {
        System.out.println("Dear User," +
                "If you are registered u can use operations such as '+', '-', '/', '*'(for example 22+3, 24/2) and functions" +
                "'sin', 'cos', 'tan', 'cot'(for example sin 23, cot 34). Otherwise, you can only use such operators as" +
                "'+', '-', '/', '*'."
        );

        HistoryHelper historyHelper = new HistoryHelperImpl();
        historyHelper.publishAction(new HistoryAction("1", Action.MINUS));

        while (true) {
            System.out.println("Are you registered?(yes/no)");
            String answer = scanner.next();

            if (Objects.equals(answer, "yes")) {
                System.out.println("Please, enter your password and login");
                System.out.print("Login: ");
                String login = scanner.next();

                System.out.print("Password: ");
                String password = scanner.next();

                LoginUser loginUser = new LoginUser(login, password);

                User user = userHelper.findUserByLogin(loginUser.getLogin())
                        .orElseThrow(() -> new RuntimeException("unable to find all users"));


            }
        }

    }
}
