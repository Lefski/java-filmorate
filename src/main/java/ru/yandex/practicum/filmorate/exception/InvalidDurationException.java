package ru.yandex.practicum.filmorate.exception;

public class InvalidDurationException extends RuntimeException {
    public InvalidDurationException(String message, Exception e) {
        super(message, e);
    }

    public InvalidDurationException(String message) {
        super(message);
    }
}
