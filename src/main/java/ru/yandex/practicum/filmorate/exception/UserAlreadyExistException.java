package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message, Exception e) {
        super(message, e);
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
