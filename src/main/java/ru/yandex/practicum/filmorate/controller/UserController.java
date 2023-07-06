package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int idCounter = 1;

    @GetMapping
    public List<User> getUsers() {
        log.debug("Текущее кол-во пользователей: {}", users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) throws UserAlreadyExistException, InvalidLoginException, InvalidEmailException, InvalidBirthdayException {
        user = checkUserValidity(user); //проверяем соответствуют ли поля и при необходимости форматируем юзера
        user.setId(idCounter);
        idCounter++;
        log.debug("Добавлен user: {}", user.toString());
        users.put(user.getId(), user);
        return user;
    }


    @PutMapping
    public User put(@RequestBody User user) throws InvalidEmailException, InvalidLoginException, InvalidBirthdayException, NoSuchUserException {
        if (!users.containsKey(user.getId())){
            log.error("Пользователя, которого нужно обновить, не существует");
            throw new NoSuchUserException("Пользователя, которого нужно обновить, не существует");
        }
        user = checkUserValidity(user);
        log.debug("Обновлен user: {}", user.toString());
        users.put(user.getId(), user);
        return user;
    }

//    private void checkIfUserAlreadyExists(User user) throws UserAlreadyExistException {
//        if (users.containsKey(user.getEmail())) {
//            log.error("User с таким email уже существует ", user);
//            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует");
//        }
//    }

    public User checkUserValidity(User user) throws InvalidLoginException, InvalidEmailException, InvalidBirthdayException {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Переданная почта некорректна ", user);
            throw new InvalidEmailException("Переданная почта некорректна");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error("Переданный логин некорректен ", user);
            throw new InvalidLoginException("Переданный логин некорректен");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Переданный день рождения некорректен ", user);
            throw new InvalidBirthdayException("Переданный день рождения некорректен");
        }

        user = formatUserName(user);
        return user;
    }

    public User formatUserName(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
            log.debug("Заменено поле name в user: {}", user.toString());
            return user;
        }
        return user;
    }

}
