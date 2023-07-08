package ru.yandex.practicum.filmorate.exceptions;

public class InvalidDescriptionException extends RuntimeException {
    public InvalidDescriptionException(String message, Exception e) {
        super(message, e);
    }

    public InvalidDescriptionException(String message) {
        super(message);
    }
}
