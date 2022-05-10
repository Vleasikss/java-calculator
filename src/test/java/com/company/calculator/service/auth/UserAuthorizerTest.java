package com.company.calculator.service.auth;

import com.company.calculator.db.UserStorageHelper;
import com.company.calculator.model.User;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserAuthorizerTest {

    private static final TestUserStorageHelper userStorageHelper = new TestUserStorageHelper();

    private static class TestUserStorageHelper extends UserStorageHelper {
        @Override
        public String storageFilename() {
            return "test-users.json";
        }

        public void deleteAll() throws IOException {
            Files.delete(userStorageHelper.storageFilePath());
            userStorageHelper.init();
        }
    }

    @Test
    public void shouldAuthUserCorrectly() {
        UserAuthorizer authorizer = new UserAuthorizer(userStorageHelper);
        Optional<User> maybeUser = authorizer.processAuthentication("login1", "password1", "password1");
        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        assertEquals(user.getLogin(), "login1");
        assertEquals(user.getPassword(), "password1");
    }

    @Test
    public void shouldNotAuthUser() {
        UserAuthorizer authorizer = new UserAuthorizer(userStorageHelper);
        Optional<User> maybeUser = authorizer.processAuthentication(null, null, null);
        assertTrue(maybeUser.isEmpty());

        Optional<User> maybeUser1 = authorizer.processAuthentication("login1", null, null);
        assertTrue(maybeUser1.isEmpty());

        Optional<User> maybeUser2 = authorizer.processAuthentication("login1", "password1", null);
        assertTrue(maybeUser2.isEmpty());

        //already exist
        Optional<User> maybeUser4 = authorizer.processAuthentication("login", "password", "password");
        assertTrue(maybeUser4.isEmpty());
    }

    @Test
    public void shouldAuthorizeUserCorrectly() {
        UserAuthorizer authorizer = new UserAuthorizer(userStorageHelper);
        Optional<User> maybeUser = authorizer.processAuthorization("login", "password");
        assertTrue(maybeUser.isPresent());
        User user = maybeUser.get();
        assertEquals(user.getLogin(), "login");
        assertEquals(user.getPassword(), "password");
    }

    @Test
    public void shouldNotAuthorizeUser() {
        UserAuthorizer authorizer = new UserAuthorizer(userStorageHelper);
        Optional<User> maybeUser1 = authorizer.processAuthorization(null, null);
        assertTrue(maybeUser1.isEmpty());

        Optional<User> maybeUser2 = authorizer.processAuthorization("login1", null);
        assertTrue(maybeUser2.isEmpty());

        Optional<User> maybeUser3 = authorizer.processAuthorization(null, "password1");
        assertTrue(maybeUser3.isEmpty());

    }


    @Before
    public void beforeEach() throws IOException {
        userStorageHelper.deleteAll();
        userStorageHelper.save(new User("1", "login", "password"));
    }

    @AfterClass
    public static void afterAll() throws IOException {
        userStorageHelper.deleteAll();
    }

}
