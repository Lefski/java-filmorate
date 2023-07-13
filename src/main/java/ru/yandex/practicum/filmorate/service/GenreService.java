package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.daoimpl.GenreDaoImpl;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class GenreService {

    private final GenreDaoImpl genreDao;

    @Autowired
    public GenreService(GenreDaoImpl genreDao) {
        this.genreDao = genreDao;
    }

    public List<Genre> getGenre() {
        log.info("Выполнен запроc на получение списка всех жанров");
        return genreDao.getGenres();
    }

    public Optional<Genre> getGenreById(int id) {
        log.info("Выполнен запроc на поиск жанра по id");
        return genreDao.getGenreById(id);
    }
}
