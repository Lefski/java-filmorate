package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @Override
    public User getUserById(Integer id) {
        if (!users.containsKey(id)) {
            throw new NoSuchUserException("Пользователя с переданным id не существует");
        }
        return users.get(id);
    }

    @Override
    public List<User> getUsers() {
        log.debug("Текущее кол-во пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User create(User user) {
        user = checkUserValidity(user); //проверяем соответствуют ли поля и при необходимости форматируем юзера
        user.setId(idCounter);
        idCounter++;
        log.debug("Добавлен user: {}", user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            log.error("Пользователя, которого нужно обновить, не существует");
            throw new NoSuchUserException("Пользователя, которого нужно обновить, не существует");
        }
        user = checkUserValidity(user);
        log.debug("Обновлен user: {}", user);
        users.put(user.getId(), user);
        return user;
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
