package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    private int idCounter = 1;

    @GetMapping
    public List<Film> getFilms() {
        log.debug("Текущее кол-во фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws FilmAlreadyExistException, InvalidNameException, InvalidDurationException, InvalidDescriptionException, InvalidReleaseDateException {
        //checkIfSameFilmAlreadyExists(film);//проверяем занят ли id
        film = checkFilmValidity(film); //проверяем соответствуют ли поля и при необходимости форматируем фильм
        film.setId(idCounter);
        idCounter++;
        log.debug("Добавлен film: {}", film.toString());
        films.put(film.getId(), film);
        return film;
    }

//    private void checkIfSameFilmAlreadyExists(Film film) throws FilmAlreadyExistException {
//        if (films.containsKey(film.getId())) {
//            log.error("Film с таким id уже существует ", film);
//            throw new FilmAlreadyExistException("Film с таким id уже существует");
//        }
//    }

    public Film checkFilmValidity(Film film) throws InvalidNameException, InvalidDescriptionException, InvalidReleaseDateException, InvalidDurationException {
        if (film.getName() == null || film.getName().isEmpty()) {
            log.error("Переданное название фильма некорректно", film);
            throw new InvalidNameException("Переданное название фильма некорректно");

        }
        if (film.getDescription().length() > 200) {
            log.error("Переданное описание фильма некорректно ", film);
            throw new InvalidDescriptionException("Переданное описание фильма некорректно");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Переданная дата выхода фильма некорректна ", film);
            throw new InvalidReleaseDateException("Переданная дата выхода фильма некорректна");
        }
        if (film.getDuration() < 0) {
            log.error("Переданная длительность фильма некорректна ", film);
            throw new InvalidDurationException("Переданная длительность фильма некорректна");
        }

        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) throws InvalidNameException, InvalidDurationException, InvalidDescriptionException, InvalidReleaseDateException, NoSuchFilmException {
        if (!films.containsKey(film.getId())){
            log.error("Фильм, который нужно обновить, не существует");
            throw new NoSuchFilmException("Фильм, который нужно обновить, не существует");
        }
        film = checkFilmValidity(film);
        log.debug("Обновлен film: {}", film.toString());
        films.put(film.getId(), film);
        return film;
    }


}
