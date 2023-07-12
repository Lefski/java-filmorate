package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreDao {

    List<Genre> getFilmGenresById(Integer filmId);
    void addGenreFilm(Integer filmId, Integer genreId);
    void updateGenresInTableFilm(Film film);
    void removeGenreFilm(Integer filmId, Integer genreId);
}
