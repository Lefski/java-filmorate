package ru.yandex.practicum.filmorate.exception;

public class NoFilmsException extends RuntimeException {
    public NoFilmsException(String message) {
        super(message);
    }
}
