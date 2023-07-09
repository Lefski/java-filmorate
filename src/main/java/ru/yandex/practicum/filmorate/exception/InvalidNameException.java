package ru.yandex.practicum.filmorate.exception;

public class InvalidNameException extends RuntimeException {
    public InvalidNameException(String message, Exception e) {
        super(message, e);
    }

    public InvalidNameException(String message) {
        super(message);
    }
}
