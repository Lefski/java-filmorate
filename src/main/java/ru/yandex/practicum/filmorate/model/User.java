package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;
    private String email;
    private String login;
    private String name;
    private LocalDate birthday;
    private Set<Integer> friends;

    public User() {
        friends = new HashSet<>();
    }

    public void addFriend(int friendId){
        friends.add(friendId);
    }

    public void removeFriend(int friendId){
        if(!friends.contains(friendId)){
            throw new NoSuchUserException("Переданного друга не существует в списке друзей");
        }
        friends.remove(friendId);
    }
}
