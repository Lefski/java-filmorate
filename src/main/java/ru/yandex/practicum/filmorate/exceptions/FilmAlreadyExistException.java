package ru.yandex.practicum.filmorate.exceptions;

public class FilmAlreadyExistException extends Exception{
    public FilmAlreadyExistException(String message, Exception e) {
        super(message, e);
    }

    public FilmAlreadyExistException(String message) {
        super(message);
    }
}
