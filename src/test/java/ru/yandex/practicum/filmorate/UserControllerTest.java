package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController();
    }

    @Test
    public void testCreateUserWithValidData() throws UserAlreadyExistException, InvalidLoginException, InvalidEmailException, InvalidBirthdayException {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        User createdUser = userController.create(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("john_doe", createdUser.getLogin());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), createdUser.getBirthday());
        assertEquals("john_doe", createdUser.getName());
    }

    @Test
    public void testCreateUserWithEmptyEmail() {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(InvalidEmailException.class, () -> userController.create(user));
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("john.doe");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(InvalidEmailException.class, () -> userController.create(user));
    }

    @Test
    public void testCreateUserWithEmptyLogin() {
        User user = new User();
        user.setLogin("");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(InvalidLoginException.class, () -> userController.create(user));
    }

    @Test
    public void testCreateUserWithInvalidLoginContainingSpaces() {
        User user = new User();
        user.setLogin("john doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(InvalidLoginException.class, () -> userController.create(user));
    }

    @Test
    public void testCreateUserWithFutureBirthday() {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.now().plusDays(1));

        assertThrows(InvalidBirthdayException.class, () -> userController.create(user));
    }

    @Test
    public void testCreateUserWithEmptyName() throws UserAlreadyExistException, InvalidLoginException, InvalidEmailException, InvalidBirthdayException {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        user.setName("");

        User createdUser = userController.create(user);

        assertNotNull(createdUser);
        assertNotNull(createdUser.getId());
        assertEquals("john_doe", createdUser.getLogin());
        assertEquals("john.doe@example.com", createdUser.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), createdUser.getBirthday());
        assertEquals("john_doe", createdUser.getName());
    }

    @Test
    public void testGetUsers() throws UserAlreadyExistException, InvalidLoginException, InvalidEmailException, InvalidBirthdayException {
        User user1 = new User();
        user1.setLogin("john_doe");
        user1.setEmail("john.doe@example.com");
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        userController.create(user1);

        User user2 = new User();
        user2.setLogin("jane_smith");
        user2.setEmail("jane.smith@example.com");
        user2.setBirthday(LocalDate.of(1995, 5, 5));
        userController.create(user2);

        List<User> users = userController.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void testUpdateUserWithValidData() throws UserAlreadyExistException, InvalidLoginException, InvalidEmailException, InvalidBirthdayException, NoSuchUserException {
        User user = new User();
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));
        User createdUser = userController.create(user);

        User updatedUser = new User();
        updatedUser.setId(createdUser.getId());
        updatedUser.setLogin("john.doe.updated");
        updatedUser.setEmail("john.doe.updated@example.com");
        updatedUser.setBirthday(LocalDate.of(1992, 2, 2));
        User result = userController.put(updatedUser);

        assertEquals(createdUser.getId(), result.getId());
        assertEquals("john.doe.updated", result.getLogin());
        assertEquals("john.doe.updated@example.com", result.getEmail());
        assertEquals(LocalDate.of(1992, 2, 2), result.getBirthday());
        assertEquals("john.doe.updated", result.getName());
    }

    @Test
    public void testUpdateNonExistingUser() {
        User user = new User();
        user.setId(12345);
        user.setLogin("john_doe");
        user.setEmail("john.doe@example.com");
        user.setBirthday(LocalDate.of(1990, 1, 1));

        assertThrows(NoSuchUserException.class, () -> userController.put(user));
    }
}
