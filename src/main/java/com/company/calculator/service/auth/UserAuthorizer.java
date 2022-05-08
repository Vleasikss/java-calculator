package com.company.calculator.service.auth;

import com.company.calculator.db.UserStorageHelper;
import com.company.calculator.model.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class UserAuthorizer {

    private static final Scanner scanner = new Scanner(System.in);
    private final UserStorageHelper userStorageHelper;

    public UserAuthorizer(UserStorageHelper userStorageHelper) {
        this.userStorageHelper = userStorageHelper;
    }

    public Optional<User> processAuthorization() {
        System.out.println("Please, enter your password and login");

        System.out.println("Enter your login: ");
        String login = scanner.next();

        System.out.println("Enter your password: ");
        String password = scanner.next();

        return validateAuthorization(login, password);
    }

    public boolean processAuthentication() {
        try {
            System.out.println("Enter your login: ");
            String login = scanner.next();

            System.out.println("Enter your password: ");
            String password = scanner.next();

            System.out.println("Repeat your password: ");
            String repeatPassword = scanner.next();

            if (!validateAuthentication(login, password, repeatPassword)) {
                return processAuthentication();
            }
            String idUser = generateUserId();
            User user = new User(idUser, login, password);
            userStorageHelper.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String generateUserId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private boolean validateAuthentication(String login, String password, String repeatPassword) {
        if (login.isEmpty()) {
            System.out.println("Login should not be empty");
            return false;
        }
        if (!Objects.equals(password, repeatPassword)) {
            System.out.println("Passwords do not match");
            return false;
        }
        Optional<User> byLogin = userStorageHelper.findByLogin(login);
        if (byLogin.isPresent()) {
            System.out.println("User with such login is already exist");
            return false;
        }
        return true;
    }

    private Optional<User> validateAuthorization(String login, String password) {
        Optional<User> byLoginAndPassword = userStorageHelper.findByLoginAndPassword(login, password);
        if (byLoginAndPassword.isEmpty()) {
            System.out.println("Sorry, we didn't find you in the system.");
        }
        return byLoginAndPassword;
    }
}
