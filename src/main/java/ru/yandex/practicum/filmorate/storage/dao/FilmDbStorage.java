package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Qualifier
@Component
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private static final LocalDate FIRST_EVER_FILM_DATE = LocalDate.of(1895, 12, 28);
    private int idCounter = 1;

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Film getFilmById(Integer id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from FILMS where FILM_ID = ?", id);
        if (filmRows.next()) {
            Film film = new Film(filmRows.getInt("FILM_ID"), filmRows.getString("NAME"),
                    filmRows.getString("DESCRIPTION"), filmRows.getDate("RELEASEDATE").toLocalDate(), filmRows.getInt("DURATION"), getGenre(filmRows.getInt("GENRE_ID")), getMpaRating(filmRows.getInt("MPA_RATING_ID")));

            log.info("Найден фильм: {} {}", film.getId(), film.getName());

            return film;
        } else {
            throw new NoSuchFilmException("Фильма с переданным id не существует");
        }
    }

    public String getGenre(Integer id) {
        if(id == 0){return "ЗАГЛУШКА";}
        SqlRowSet genreRaw = jdbcTemplate.queryForRowSet("select NAME from GENRE where GENRE_ID = ?", id);
        if (genreRaw.next()) {
            String genre = genreRaw.getString("NAME");
            return genre;
        } else throw new ValidationException("Такого жанра не существует");
    }

    public MpaRating getMpaRating(Integer id) {
        SqlRowSet mpaRaw = jdbcTemplate.queryForRowSet("select MPA_RATING from MPA_RATING where MPA_RATING_ID = ?", id);
        if (mpaRaw.next()) {
            MpaRating rating = new MpaRating(id, mpaRaw.getString("MPA_RATING"));
            return rating;
        } else throw new ValidationException("Такого рейтинга не существует");
    }

    @Override
    public List<Film> getFilms() {
        String sql = "select * from FILMS";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));

        log.debug("Текущее кол-во фильмов: {}", films.size());
        return new ArrayList<>(films);
    }

    private Film makeFilm(ResultSet filmRows) throws SQLException {
        return new Film(filmRows.getInt("FILM_ID"), filmRows.getString("NAME"), filmRows.getString("DESCRIPTION"), filmRows.getDate("RELEASEDATE").toLocalDate(), filmRows.getInt("DURATION"),
                getGenre(filmRows.getInt("GENRE_ID")), getMpaRating(filmRows.getInt("MPA_RATING_ID")));
    }

    @Override
    public Film create(Film film) {
        checkFilmValidity(film); //проверяем соответствуют ли поля и при необходимости форматируем фильм
        film.setId(idCounter);
        idCounter++;
        log.debug("Добавлен film: {}", film);
        save(film);
        return film;
    }

    private void save(Film film) {
        String sqlQuery = "insert into FILMS(FILM_ID, NAME, DESCRIPTION, RELEASEDATE, DURATION, GENRE_ID, MPA_RATING_ID) " + "values (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre(),
                film.getMpa().getId()
        );

    }

    @Override
    public Film update(Film film) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("select * from FILMS where FILM_ID = ?", film.getId());

        if (!filmRows.next()) {
            log.error("Фильм, который нужно обновить, не существует");
            throw new NoSuchFilmException("Фильм, который нужно обновить, не существует");
        }
        checkFilmValidity(film);
        log.debug("Обновлен film: {}", film);
        saveUpdate(film);
        return film;
    }

    private void saveUpdate(Film film){
        String sqlQuery = "update FILMS set " + "FILM_ID = ?, NAME = ?, DESCRIPTION = ?, RELEASEDATE = ?, DURATION = ?, GENRE_ID = ?, MPA_RATING_ID = ?";
        jdbcTemplate.update(
                sqlQuery,
                film.getId(),
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getGenre(),
                film.getMpa().getId()
        );
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
