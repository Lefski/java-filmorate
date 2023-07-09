package ru.yandex.practicum.filmorate.exception;

public class UserHaveNoFriendsException extends RuntimeException {
    public UserHaveNoFriendsException(String message) {
        super(message);
    }
}
