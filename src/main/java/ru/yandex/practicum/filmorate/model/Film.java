package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;

import java.time.LocalDate;
import java.util.HashSet;

@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private HashSet<Integer> likes = new HashSet<>();//храним id лайкнувших пользователей

    public Film() {
    }

    public void addLike(int userId) {
        likes.add(userId);
    }

    public void removeLike(int userId) {
        if (!likes.contains(userId)) {
            throw new NoSuchUserException("Такой пользователь не ставил лайк");
        }
        likes.remove(id);
    }

}
