package com.company.calculator.db;

import com.company.calculator.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserStorageHelperTest {

    private static final UserStorageHelper userStorageHelper = new TestUserStorageHelper();
    private final ObjectMapper mapper = new ObjectMapper();

    private static class TestUserStorageHelper extends UserStorageHelper {
        @Override
        public String storageFilename() {
            return "test-users.json";
        }
    }

    @Test
    public void shouldSaveUser() throws IOException {
        User user = new User("1", "login1", "password1");

        userStorageHelper.save(user);

        String s = Files.readString(userStorageHelper.storageFilePath());
        List<User> list = mapper.readValue(s, userStorageHelper.listClazz());
        assertEquals(list.size(), 1);
        assertEquals(user, list.get(0));
    }

    @Test
    public void shouldFindUserById() throws IOException {
        User user = new User("1", "login1", "password1");

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        Files.writeString(userStorageHelper.storageFilePath(), json);

        User byId = userStorageHelper.findById("1").orElse(null);
        assertNotNull(byId);
        assertEquals(byId, user);
    }

    @Test
    public void shouldFindAllUsers() throws IOException {
        User user = new User("1", "login1", "password1");

        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        Files.writeString(userStorageHelper.storageFilePath(), json);

        List<User> all = userStorageHelper.findAll();
        assertNotNull(all);
        assertEquals(all, List.of(user));
    }

    @Before
    public void beforeEach() throws IOException {
        Files.delete(userStorageHelper.storageFilePath());
        userStorageHelper.init();
    }

    @AfterClass
    public static void afterAll() throws IOException {
        Files.delete(userStorageHelper.storageFilePath());
    }

}
