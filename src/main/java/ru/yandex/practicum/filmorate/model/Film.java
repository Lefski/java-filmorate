package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private List<Genre> genres;
    private Mpa mpa;
    private Integer rate;
    private HashSet<Integer> likes = new HashSet<>();//храним id лайкнувших пользователей


    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, List<Genre> genres, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public List<Genre> getGenres() {
        if (genres == null){
            List<Genre> genreArrayList= new ArrayList<>();
            genreArrayList.add(new Genre(1));
            return genreArrayList;
        }
        return genres;
    }

    public Film() {
    }

    public Integer getRate() {
        if (rate == null){
            return 0;
        }
        return rate;
    }

    public void addLike(int userId) {
        likes.add(userId);
    }

    public void removeLike(int userId) {
        if (!likes.contains(userId)) {
            throw new NoSuchUserException("Такой пользователь не ставил лайк");
        }
        likes.remove(userId);
    }

}
