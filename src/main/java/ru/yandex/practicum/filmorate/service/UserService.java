package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserStorage inMemoryUserStorage;

    public List<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User create(User user) {
        return inMemoryUserStorage.create(user);
    }

    public User update(User user) {
        return inMemoryUserStorage.update(user);
    }

    public void addFriend(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.addFriend(friendId);
        inMemoryUserStorage.update(user);

        friend.addFriend(userId);
        inMemoryUserStorage.update(friend);
    }

    public void removeFriend(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);

        user.removeFriend(friendId);
        inMemoryUserStorage.update(user);

        friend.removeFriend(userId);
        inMemoryUserStorage.update(friend);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        User user = inMemoryUserStorage.getUserById(userId);
        User friend = inMemoryUserStorage.getUserById(friendId);
        if(user.getFriends() == null || friend.getFriends() == null){
            return Collections.emptyList();
        }
        ArrayList<Integer> commonFriendsIds = new ArrayList<>(user.getFriends());
        commonFriendsIds.retainAll(friend.getFriends());

        ArrayList<User> commonFriends = new ArrayList<>();
        for (Integer id :
                commonFriendsIds) {
            commonFriends.add(inMemoryUserStorage.getUserById(id));
        }
        return commonFriends;
    }

    public User getUserById(int id){
        return inMemoryUserStorage.getUserById(id);
    }

    public List<User> getFriends(int userId) {
        User user = inMemoryUserStorage.getUserById(userId);
        if(user.getFriends() == null){
            return Collections.emptyList();
        }
        ArrayList<Integer> friendsIds = new ArrayList<>(user.getFriends());

        ArrayList<User> friends = new ArrayList<>();
        for (Integer id :
                friendsIds) {
            friends.add(inMemoryUserStorage.getUserById(id));
        }
        return friends;
    }




}
