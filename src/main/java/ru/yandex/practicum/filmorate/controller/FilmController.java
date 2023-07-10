package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получен запроc на получение списка всех фильмов");
        return filmService.getFilms();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        log.info("Получен запроc на добавление лайка на фильм");
        return filmService.create(film);
    }


    @PutMapping
    public Film update(@RequestBody Film film) {
        log.info("Получен запроc на обновление фильма");
        return filmService.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запроc на добавление лайка на фильм");
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void dislikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("Получен запроc на удаление лайка с фильма");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Получен запроc на поиск фильма по id");
        return filmService.getFilmById(id);
    }

    @GetMapping("/popular")
    public List<Film> getPopularFilms(@RequestParam(required = false, defaultValue = "10") int count) {
        log.info("Получен запроc на получение списка популярных фильмов");
        return filmService.getTopFilms(count);
    }
}
