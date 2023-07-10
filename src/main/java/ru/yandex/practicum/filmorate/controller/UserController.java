package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info("Получен запроc на получение списка всех пользователей");
        return userService.getUsers();
    }


    @PostMapping
    public User create(@RequestBody User user) {
        log.info("Получен запроc на создание пользователя");
        return userService.create(user);
    }


    @PutMapping()
    public User update(@RequestBody User user) {
        log.info("Получен запроc на обновление пользователя");
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запроc на добавление пользователя в друзья");
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable int id, @PathVariable int friendId) {
        log.info("Получен запроc на удаление пользователя из друзей");
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        log.info("Получен запроc на получение пользователя по id");
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        log.info("Получен запроc на получение списка друзей пользователя");
        return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable int id, @PathVariable int otherId) {
        log.info("Получен запроc на получение списка общих друзей двух пользователей");
        return userService.getCommonFriends(id, otherId);
    }


}
