package ru.yandex.practicum.filmorate.exception;

public class NoSuchGenreException extends RuntimeException {
    public NoSuchGenreException(String message) {
        super(message);
    }
}
