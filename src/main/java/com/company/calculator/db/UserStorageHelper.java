package com.company.calculator.db;

import com.company.calculator.model.User;

import java.util.Optional;

public class UserStorageHelper extends AbstractInMemoryStorageHelper<User> {

    public UserStorageHelper() {
        super();
    }

    @Override
    public String storageFilename() {
        return "users.json";
    }

    @Override
    protected Class<User> clazz() {
        return User.class;
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return findAll().stream().filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password)).findFirst();
    }

    public Optional<User> findByLogin(String login) {
        return findAll().stream().filter(user -> user.getLogin().equals(login)).findAny();
    }
}
