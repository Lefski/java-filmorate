package ru.yandex.practicum.filmorate.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message, Exception e) {
        super(message, e);
    }

    public InvalidEmailException(String message) {
        super(message);
    }
}
