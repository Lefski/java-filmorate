package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.daoimpl.GenreDaoImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {

    private final GenreDaoImpl genreDao;

    @GetMapping
    public List<Genre> getGenre() {
        log.info("Получен запроc на получение списка всех жанров");
        return genreDao.getGenres();
    }

    @GetMapping("/{id}")
    public Optional<Genre> getGenreById(@PathVariable int id) {
        log.info("Получен запроc на поиск жанра по id");
        return genreDao.getGenreById(id);
    }

}
