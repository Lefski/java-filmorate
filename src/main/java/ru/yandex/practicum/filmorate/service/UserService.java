package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    @Qualifier("userDbStorage")
    @Autowired
    private final UserStorage inMemoryUserStorage;

    public List<User> getUsers() {
        log.info("Выполнен запроc на получение списка пользователей");
        return inMemoryUserStorage.getUsers();
    }

    public User create(User user) {
        log.info("Выполнен запроc на создание пользователя");
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        log.info("Выполнен запроc на обновление пользователя");
        return inMemoryUserStorage.update(user);
    }

    public void addFriend(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.addFriend(friendId);
        inMemoryUserStorage.update(user);

        friend.addFriend(userId);
        inMemoryUserStorage.update(friend);
        log.info("Выполнен запроc на добавление пользователя в друзья");

    }

    public void removeFriend(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.removeFriend(friendId);
        inMemoryUserStorage.update(user);

        friend.removeFriend(userId);
        inMemoryUserStorage.update(friend);
        log.info("Выполнен запроc на добавление пользователя из друзей");

    }

    public List<User> getCommonFriends(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);
        if (user.getFriends() == null || friend.getFriends() == null) {
            log.info("Выполнен запроc на получение списка общих друзей, список пуст");
            return Collections.emptyList();
        }
        ArrayList<Integer> commonFriendsIds = new ArrayList<>(user.getFriends());
        commonFriendsIds.retainAll(friend.getFriends());

        ArrayList<User> commonFriends = new ArrayList<>();
        for (Integer id : commonFriendsIds) {
            commonFriends.add(inMemoryUserStorage.getUserById(id));
        }
        log.info("Выполнен запроc на получение списка общих друзей");

        return commonFriends;
    }

    public User getUserById(int id) {
        log.info("Выполнен запроc на получение пользователя по id");
        return inMemoryUserStorage.getUserById(id);
    }

    public List<User> getFriends(int userId) {
        User user = inMemoryUserStorage.getUserById(userId);
        if (user.getFriends() == null) {
            log.info("Выполнен запроc на получение списка друзей пользователя");
            return Collections.emptyList();
        }
        ArrayList<Integer> friendsIds = new ArrayList<>(user.getFriends());

        ArrayList<User> friends = new ArrayList<>();
        for (Integer id : friendsIds) {
            friends.add(inMemoryUserStorage.getUserById(id));
        }
        log.info("Выполнен запроc на получение списка друзей пользователя");
        return friends;
    }


}
