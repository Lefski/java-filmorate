package ru.yandex.practicum.filmorate.storage.dao.daoimpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Qualifier("userDbStorage")
@Component
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private int idCounter = 1;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where USER_ID = ?", id);
        if (userRows.next()) {
            User user = new User(userRows.getInt("USER_ID"), userRows.getString("EMAIL"), userRows.getString("LOGIN"), userRows.getString("NAME"), userRows.getDate("BIRTHDAY").toLocalDate());

            log.info("Найден пользователь: {} {}", user.getId(), user.getLogin());

            return user;
        } else {
            throw new NoSuchUserException("Пользователя с переданным id не существует");
        }
    }

    @Override
    public List<User> getUsers() {
        String sql = "select * from USERS";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        log.debug("Текущее кол-во пользователей: {}", users.size());
        return new ArrayList<>(users);
    }

    private User makeUser(ResultSet userRows) throws SQLException {
        return new User(userRows.getInt("USER_ID"), userRows.getString("EMAIL"), userRows.getString("LOGIN"), userRows.getString("NAME"), userRows.getDate("BIRTHDAY").toLocalDate());
    }

    @Override
    public User create(User user) {
        user = checkUserValidity(user); //проверяем соответствуют ли поля и при необходимости форматируем юзера
        user.setId(idCounter);
        idCounter++;
        log.debug("Добавлен user: {}", user);
        save(user);
        return user;
    }

    public void save(User user) {
        String sqlQuery = "insert into USERS(USER_ID, EMAIL, LOGIN, NAME, BIRTHDAY) " + "values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
    }

    @Override
    public User update(User user) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS where USER_ID = ?", user.getId());
        if (!userRows.next()) {
            log.error("Пользователя, которого нужно обновить, не существует");
            throw new NoSuchUserException("Пользователя, которого нужно обновить, не существует");
        }
        user = checkUserValidity(user);
        log.debug("Обновлен user: {}", user);
        saveUpdate(user);
        return user;
    }

    public void saveUpdate(User user) {
        String sqlQuery = "update USERS set " + "USER_ID = ?, EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ?";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
    }

    private User checkUserValidity(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            log.error("Переданная почта некорректна: {}", user);
            throw new ValidationException("Переданная почта некорректна");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            log.error("Переданный логин некорректен: {}", user);
            throw new ValidationException("Переданный логин некорректен");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Переданный день рождения некорректен: {}", user);
            throw new ValidationException("Переданный день рождения некорректен");
        }

        user = formatUserName(user);
        return user;
    }

    private User formatUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
            log.debug("Заменено поле name в user: {}", user);
            return user;
        }
        return user;
    }
}
