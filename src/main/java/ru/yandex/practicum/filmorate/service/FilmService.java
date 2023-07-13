package ru.yandex.practicum.filmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikesDao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmDbStorage;
    private final FilmLikesDao filmLikesDao;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmDbStorage, FilmLikesDao filmLikesDao) {
        this.filmDbStorage = filmDbStorage;
        this.filmLikesDao = filmLikesDao;
    }

    public List<Film> getFilms() {
        log.info("Выполнен запроc на получение всех фильмов");
        return filmDbStorage.getFilms();
    }

    public Film create(Film film) {
        log.info("Выполнен запроc на создание фильма");
        return filmDbStorage.create(film);
    }


    public Film update(Film film) {
        log.info("Выполнен запроc на обновление фильма");
        return filmDbStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        Film film = filmDbStorage.getFilmById(filmId);
        filmLikesDao.addLike(filmId, userId);
        film.setRate(film.getRate() + 1);
        filmDbStorage.update(film);
        log.info("Выполнен запроc на добавление лайка для фильма");
    }

    public void removeLike(int filmId, int userId) {
        Film film = filmDbStorage.getFilmById(filmId);
        filmLikesDao.removeLike(filmId, userId);
        film.setRate(film.getRate() - 1);
        filmDbStorage.update(film);
        log.info("Выполнен запроc на удаление лайка для фильма");
    }

    public List<Film> getTopFilms(int count) {
        List<Film> allFilms = filmDbStorage.getFilms();
        if (allFilms.isEmpty()) {
            log.info("Выполнен запроc на получение списка лучших фильмов, список пуст");
            return Collections.emptyList();
        }
        for (Film film : allFilms) {
            if (film.getRate() == 0) {
                film.setRate(filmLikesDao.getFilmLikesById(film.getId()).size());
            }
        }
        allFilms.sort(Comparator.comparingInt(Film::getRate));
        Collections.reverse(allFilms);
        log.info("Выполнен запроc на получение списка лучших фильмов");
        // Возвращаем count наиболее популярных фильмов, если count больше кол-ва фильмов, то выводим что есть
        return allFilms.subList(0, Math.min(count, allFilms.size()));
    }

    public Film getFilmById(int id) {
        log.info("Выполнен запроc на получение фильма по id");
        return filmDbStorage.getFilmById(id);
    }


}
