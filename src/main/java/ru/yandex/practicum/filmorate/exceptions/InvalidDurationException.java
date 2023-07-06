package ru.yandex.practicum.filmorate.exceptions;

public class InvalidDurationException extends Exception {
    public InvalidDurationException(String message, Exception e) {
        super(message, e);
    }

    public InvalidDurationException(String message) {
        super(message);
    }
}
