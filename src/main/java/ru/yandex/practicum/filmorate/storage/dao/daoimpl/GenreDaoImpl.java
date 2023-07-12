package ru.yandex.practicum.filmorate.storage.dao.daoimpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchGenreException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.GenreDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Genre> getGenreById(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from GENRE where GENRE_ID = ?", id);
        if (userRows.next()) {
            Genre genre = new Genre(userRows.getInt("GENRE_ID"), userRows.getString("NAME"));

            log.info("Найден жанр: {} {}", genre.getId(), genre.getName());

            return Optional.of(genre);
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            throw new NoSuchGenreException("Жанр с идентификатором {} не найден.");
        }

    }

    @Override
    public List<Genre> getGenres() {
        String sql = "select * from GENRE";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
    }


    private Genre makeGenre(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("GENRE_ID");
        String name = rs.getString("NAME");
        return new Genre(id, name);
    }


}
