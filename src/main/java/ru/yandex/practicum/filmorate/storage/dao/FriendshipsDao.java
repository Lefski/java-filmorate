package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface FriendshipsDao {
    List<Integer> getFriendsListByUserId(Integer id);

    void addFriend(Integer userId, Integer friendId);

    void removeFriend(Integer userId, Integer friendId);

}
