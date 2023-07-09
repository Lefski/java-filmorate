package ru.yandex.practicum.filmorate.exception;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String message, Exception e) {
        super(message, e);
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
