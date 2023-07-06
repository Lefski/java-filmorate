package ru.yandex.practicum.filmorate.exceptions;

public class InvalidReleaseDateException extends Exception{
    public InvalidReleaseDateException(String message, Exception e) {
        super(message, e);
    }

    public InvalidReleaseDateException(String message) {
        super(message);
    }
}
