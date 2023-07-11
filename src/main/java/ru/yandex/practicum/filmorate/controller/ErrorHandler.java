package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.*;

import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum")
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(final RuntimeException e) {
        log.error("ErrorHandler поймал ValidationException: {}", e.getMessage());
        return Map.of("ValidationException", e.getMessage());
    }

    @ExceptionHandler({NoFilmsException.class,NoSuchMpaException.class, NoSuchFilmException.class,NoSuchGenreException.class, NoSuchUserException.class, UserHaveNoFriendsException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundException(final RuntimeException e) {
        log.error("ErrorHandler поймал NotFoundException: {}", e.getMessage());
        return Map.of("NotFoundException", e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleInternalServerError(final RuntimeException e) {
        log.error("ErrorHandler поймал InternalServerError: {}", e.getMessage());
        return Map.of("InternalServerError", e.getMessage());
    }


}
