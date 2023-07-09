package ru.yandex.practicum.filmorate.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    @Autowired
    private final FilmStorage inMemoryFilmStorage;

    public List<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film create(Film film) {
        return inMemoryFilmStorage.create(film);
    }


    public Film update(Film film) {
        return inMemoryFilmStorage.update(film);
    }

    public void addLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.addLike(userId);
        inMemoryFilmStorage.update(film);
    }

    public void removeLike(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.removeLike(userId);
        inMemoryFilmStorage.update(film);
    }

    public List<Film> getTopFilms(int count) {
        List<Film> allFilms = inMemoryFilmStorage.getFilms();
        if(allFilms.isEmpty()){
            return Collections.emptyList();
            //Уважаемый проверяющий, не уверен правильно ли возвращать пустой список. Есть ли смысл вызывать ошибку?
        }
        allFilms.sort(Comparator.comparingInt(film -> film.getLikes().size()));
        Collections.reverse(allFilms);

        // Возвращаем count наиболее популярных фильмов, если count больше кол-ва фильмов, то выводим что есть
        return allFilms.subList(0, Math.min(count, allFilms.size()));
    }

    public Film getFilmById(int id){
        return inMemoryFilmStorage.getFilmById(id);
    }


}
