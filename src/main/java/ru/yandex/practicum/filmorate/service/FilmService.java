package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private final FilmStorage inMemoryFilmStorage;

    public List<Film> getFilms() {
        log.info("Выполнен запроc на обновление фильма");
        return inMemoryFilmStorage.getFilms();
    }

    public Film create(Film film) {
        log.info("Выполнен запроc на создание фильма");
        return inMemoryFilmStorage.create(film);
    }


    public Film update(Film film) {
        log.info("Выполнен запроc на обновление фильма");
        return inMemoryFilmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.addLike(userId);
        inMemoryFilmStorage.update(film);
        log.info("Выполнен запроc на добавление лайка для фильма");
    }

    public void removeLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.removeLike(userId);
        inMemoryFilmStorage.update(film);
        log.info("Выполнен запроc на удаление лайка для фильма");
    }

    public List<Film> getTopFilms(int count) {
        List<Film> allFilms = inMemoryFilmStorage.getFilms();
        if (allFilms.isEmpty()) {
            log.info("Выполнен запроc на получение списка лучших фильмов, список пуст");
            return Collections.emptyList();
        }
        allFilms.sort(Comparator.comparingInt(film -> film.getLikes().size()));
        Collections.reverse(allFilms);
        log.info("Выполнен запроc на получение списка лучших фильмов");
        // Возвращаем count наиболее популярных фильмов, если count больше кол-ва фильмов, то выводим что есть
        return allFilms.subList(0, Math.min(count, allFilms.size()));
    }

    public Film getFilmById(int id) {
        log.info("Выполнен запроc на получение фильма по id");
        return inMemoryFilmStorage.getFilmById(id);
    }


}
