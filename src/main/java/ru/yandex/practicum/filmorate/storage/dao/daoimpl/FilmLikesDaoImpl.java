package ru.yandex.practicum.filmorate.storage.dao.daoimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchLikeException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.dao.FilmLikesDao;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class FilmLikesDaoImpl implements FilmLikesDao {

    private final FilmDbStorage filmDbStorage;
    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(Integer filmId, Integer userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        if (!getFilmLikesById(filmId).contains(userId)) {
            String sqlQuery = "insert into FILM_LIKES(FILM_ID, USER_ID) " + "values (?, ?)";
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } else throw new ValidationException("Like on film " + filmId + " by user " + userId + " already exists");

    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {
        checkFilmId(filmId);
        checkUserId(userId);
        if (getFilmLikesById(filmId).contains(userId)) {
            String sqlQuery = "delete from FILM_LIKES where FILM_ID = ? AND USER_ID = ?";
            jdbcTemplate.update(sqlQuery, filmId, userId);
        } else throw new NoSuchLikeException("Переданный лайк не существует");
    }

    @Override
    public List<Integer> getFilmLikesById(Integer filmId) {
        checkFilmId(filmId);
        String sql = "SELECT USER_ID FROM FILM_LIKES WHERE FILM_ID = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, filmId);
    }

    private void checkUserId(Integer id) {
        userDbStorage.getUserById(id);
    }

    private void checkFilmId(Integer id) {
        filmDbStorage.getFilmById(id);
    }
}
