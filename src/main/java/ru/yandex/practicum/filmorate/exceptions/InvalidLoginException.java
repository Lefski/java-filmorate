package ru.yandex.practicum.filmorate.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message, Exception e) {
        super(message, e);
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
