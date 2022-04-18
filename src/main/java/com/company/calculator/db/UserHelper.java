package com.company.calculator.db;

import com.company.calculator.model.User;

import java.util.List;
import java.util.Optional;

public interface UserHelper {

    Optional<User> findUserByLogin(String id);

    Optional<List<User>> findAllUsers();

}
