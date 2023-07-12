package ru.yandex.practicum.filmorate.storage.dao;

import java.util.List;

public interface FilmLikesDao {

    void addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
    List<Integer> getFilmLikesById(Integer filmId);
}
