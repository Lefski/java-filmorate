package ru.yandex.practicum.filmorate.exceptions;

public class InvalidBirthdayException extends RuntimeException {
    public InvalidBirthdayException(String message) {
        super(message);
    }

    InvalidBirthdayException(String message, Exception e) {
        super(message, e);
    }
}
