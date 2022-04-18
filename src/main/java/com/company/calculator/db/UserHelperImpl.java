package com.company.calculator.db;

import com.company.calculator.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class UserHelperImpl implements UserHelper {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String USERS_PATH = "/data/users.json";

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(USERS_PATH);
            List<User> users = (List<User>) objectMapper.readValue(resourceAsStream, List.class);
            return users.stream().filter(user -> user.getLogin().equals(login)).findFirst();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<User>> findAllUsers() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(USERS_PATH);
            List<User> users = (List<User>) objectMapper.readValue(inputStream, List.class);
            return Optional.of(users);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

}
