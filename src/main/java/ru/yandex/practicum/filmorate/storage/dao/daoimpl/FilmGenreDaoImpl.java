package ru.yandex.practicum.filmorate.storage.dao.daoimpl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.FilmGenreDao;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class FilmGenreDaoImpl implements FilmGenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getFilmGenresById(Integer filmId) {
        //поставить проверки filmId
        String sql = "SELECT GENRE_ID FROM FILM_GENRE WHERE FILM_ID = ?";
        List<Integer> genresIds = jdbcTemplate.queryForList(sql, Integer.class, filmId);
        List<Genre> genres = new ArrayList<>();
        for (Integer id: genresIds) {
            genres.add(new Genre(id));
        }
        return genres;
    }

}
