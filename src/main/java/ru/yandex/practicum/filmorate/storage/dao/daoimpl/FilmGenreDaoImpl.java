package ru.yandex.practicum.filmorate.storage.dao.daoimpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchLikeException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenreDao;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class FilmGenreDaoImpl implements FilmGenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;

    private static List<Genre> getDistinct(List<Genre> genres) {
        Set<Genre> set = new HashSet<>(genres);
        genres.clear();
        genres.addAll(set);
        return genres;
    }

    @Override
    public List<Genre> getFilmGenresById(Integer filmId) {
        //поставить проверки filmId
        String sql = "SELECT GENRE_ID FROM FILM_GENRE WHERE FILM_ID = ?";
        List<Integer> genresIds = jdbcTemplate.queryForList(sql, Integer.class, filmId);
        List<Genre> genres = new ArrayList<>();
        for (Integer id : genresIds) {
            sql = "SELECT NAME FROM GENRE WHERE GENRE_ID = ?";
            List<String> str = jdbcTemplate.queryForList(sql, String.class, id);
            genres.add(new Genre(id, str.get(0)));
        }
        return getDistinct(genres);
    }

    @Override
    public void addGenreFilm(Integer filmId, Integer genreId) {

        if (!getFilmGenresById(filmId).contains(genreDao.getGenreById(genreId))) {
            String sqlQuery = "insert into FILM_GENRE(FILM_ID, GENRE_ID) " + "values (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        } else throw new ValidationException("Genre " + genreId + " of film " + filmId + " already exists in db");
    }

    @Override
    public void updateGenresInTableFilm(Film film) {

        List<Integer> externalGenres = extractIntegerList(film.getGenres());
        List<Integer> savedGenres = extractIntegerList(getFilmGenresById(film.getId()));

        if (externalGenres.equals(savedGenres)) {
            return;
        }
        for (int i : externalGenres) {
            if (!savedGenres.contains(i)) {
                addGenreFilm(film.getId(), i);
            }
        }
        for (int i : savedGenres) {
            if (!externalGenres.contains(i)) {
                removeGenreFilm(film.getId(), i);
            }
        }
    }

    private List<Integer> extractIntegerList(List<Genre> genresList) {
        List<Integer> integerList = new ArrayList<>();
        for (Genre genre : genresList) {
            integerList.add(genre.getId());
        }
        return integerList;
    }

    @Override
    public void removeGenreFilm(Integer filmId, Integer genreId) {
        boolean foundGenre = false;
        for (Genre genre : getFilmGenresById(filmId)) {
            if (genre.getId() == genreId) {
                foundGenre = true;
                break;
            }
        }

        if (foundGenre) {
            String sqlQuery = "DELETE FROM FILM_GENRE WHERE FILM_ID = ? AND GENRE_ID = ?";
            jdbcTemplate.update(sqlQuery, filmId, genreId);
        } else {
            throw new NoSuchLikeException("Переданного жанра нет в базе данных");
        }
    }


}
