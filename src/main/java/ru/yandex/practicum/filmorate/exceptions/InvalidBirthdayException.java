package ru.yandex.practicum.filmorate.exceptions;

public class InvalidBirthdayException extends Exception{
    InvalidBirthdayException (String message, Exception e){
        super(message, e);
    }

    public InvalidBirthdayException(String message){
        super(message);
    }
}
