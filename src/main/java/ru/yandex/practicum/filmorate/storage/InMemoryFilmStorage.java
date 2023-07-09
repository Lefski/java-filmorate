package ru.yandex.practicum.filmorate.storage;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private static final LocalDate FIRST_EVER_FILM_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();
    private int idCounter = 1;

    @Override
    public Film getFilmById(Integer id) {
        if (!films.containsKey(id)) {
            throw new NoSuchFilmException("Фильма с переданным id не существует");
        }
        return films.get(id);
    }

    public List<Film> getFilms() {
        log.debug("Текущее кол-во фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film create(Film film) {
        checkFilmValidity(film); //проверяем соответствуют ли поля и при необходимости форматируем фильм
        film.setId(idCounter);
        idCounter++;
        log.debug("Добавлен film: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.error("Фильм, который нужно обновить, не существует");
            throw new NoSuchFilmException("Фильм, который нужно обновить, не существует");
        }
        checkFilmValidity(film);
        log.debug("Обновлен film: {}", film);
        films.put(film.getId(), film);
        return film;
    }

    private void checkFilmValidity(Film film) {
        if (film.getName() == null || film.getName().isBlank()) {
            log.error("Переданное название фильма некорректно: {}", film);
            throw new ValidationException("Переданное название фильма некорректно");

        }
        if (film.getDescription().length() > 200) {
            log.error("Переданное описание фильма некорректно: {}", film);
            throw new ValidationException("Переданное описание фильма некорректно");
        }
        if (film.getReleaseDate().isBefore(FIRST_EVER_FILM_DATE)) {
            log.error("Переданная дата выхода фильма некорректна: {}", film);
            throw new ValidationException("Переданная дата выхода фильма некорректна");
        }
        if (film.getDuration() < 0) {
            log.error("Переданная длительность фильма некорректна: {}", film);
            throw new ValidationException("Переданная длительность фильма некорректна");
        }

    }


}
