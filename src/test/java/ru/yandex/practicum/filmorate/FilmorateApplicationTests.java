package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.daoimpl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.daoimpl.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final FilmDbStorage filmStorage;
    private final UserDbStorage userStorage;

    @Test
    void contextLoads() {
    }

    @Test
    public void testFindUserById() {
        User testUser = new User(1, "test@example.com", "testuser", "Test User", LocalDate.of(2000, 1, 1));
        userStorage.create(testUser);

        Integer userId = testUser.getId();

        User retrievedUser = userStorage.getUserById(userId);

        assertEquals(testUser.getId(), retrievedUser.getId());
        assertEquals(testUser.getEmail(), retrievedUser.getEmail());
        assertEquals(testUser.getLogin(), retrievedUser.getLogin());
        assertEquals(testUser.getName(), retrievedUser.getName());
        assertEquals(testUser.getBirthday(), retrievedUser.getBirthday());
    }

    @Test
    public void testGetUsers() {
        User user1 = new User(1, "user1@example.com", "user1", "User 1", LocalDate.of(2000, 1, 1));
        User user2 = new User(2, "user2@example.com", "user2", "User 2", LocalDate.of(2000, 2, 2));
        userStorage.create(user1);
        userStorage.create(user2);

        List<User> users = userStorage.getUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void testCreateUser() {
        User testUser = new User(1, "test@example.com", "testuser", "Test User", LocalDate.of(2000, 1, 1));

        User createdUser = userStorage.create(testUser);

        Integer userId = createdUser.getId();

        User retrievedUser = userStorage.getUserById(userId);

        assertEquals(testUser.getId(), retrievedUser.getId());
        assertEquals(testUser.getEmail(), retrievedUser.getEmail());
        assertEquals(testUser.getLogin(), retrievedUser.getLogin());
        assertEquals(testUser.getName(), retrievedUser.getName());
        assertEquals(testUser.getBirthday(), retrievedUser.getBirthday());
    }

    @Test
    public void testCreateUserWithInvalidEmail() {
        User invalidUser = new User(1, "invalid_email", "user", "Invalid User", LocalDate.of(2000, 1, 1));

        assertThrows(ValidationException.class, () -> userStorage.create(invalidUser));
    }

    @Test
    public void testUpdateNonexistentUser() {
        User testUser = new User(1, "test@example.com", "testuser", "Test User", LocalDate.of(2000, 1, 1));

        assertThrows(NoSuchUserException.class, () -> userStorage.update(testUser));
    }

    @Test
    public void testFormatUserNameWhenNameIsBlank() {
        User user = new User(1, "user@example.com", "user", "", LocalDate.of(2000, 1, 1));

        User createdUser = userStorage.create(user);

        assertEquals(user.getLogin(), createdUser.getName());
    }

    @Test
    public void testGetFilmById() {
        Film testFilm = new Film(1, List.of(new Genre(1), new Genre(2)), "Test Film", "Test Description", LocalDate.of(2023, 1, 1), 120, new Mpa(1, "PG"));

        filmStorage.create(testFilm);

        Integer filmId = testFilm.getId();

        Film retrievedFilm = filmStorage.getFilmById(filmId);

        assertEquals(testFilm.getId(), retrievedFilm.getId());
        assertEquals(testFilm.getName(), retrievedFilm.getName());
        assertEquals(testFilm.getDescription(), retrievedFilm.getDescription());
        assertEquals(testFilm.getReleaseDate(), retrievedFilm.getReleaseDate());
        assertEquals(testFilm.getDuration(), retrievedFilm.getDuration());
        assertEquals(testFilm.getMpa().getId(), retrievedFilm.getMpa().getId());
        assertEquals(testFilm.getGenres().size(), retrievedFilm.getGenres().size());
    }

    @Test
    public void testGetFilms() {
        // Создаем несколько фильмов для теста
        Film film1 = new Film(1, List.of(new Genre(1), new Genre(2)), "Film 1", "Description 1", LocalDate.of(2023, 1, 1), 120, new Mpa(1, "PG"));
        Film film2 = new Film(2, List.of(new Genre(3), new Genre(4)), "Film 2", "Description 2", LocalDate.of(2023, 2, 2), 90, new Mpa(2, "R"));
        filmStorage.create(film1);
        filmStorage.create(film2);

        List<Film> films = filmStorage.getFilms();

        assertEquals(2, films.size());
    }

    @Test
    public void testCreateFilmWithInvalidName() {
        Film invalidFilm = new Film(1, List.of(new Genre(1), new Genre(2)), "", "Test Description", LocalDate.of(2023, 1, 1), 120, new Mpa(1, "PG"));

        assertThrows(ValidationException.class, () -> filmStorage.create(invalidFilm));
    }


}
