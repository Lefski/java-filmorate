package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private Integer duration;
    private Set<Integer> likes;//храним id лайкнувших пользователей

    public Film(){
        likes = new HashSet<>();
    }

    public void addLike(int userId) {
        likes.add(userId);
    }

    public void removeLike(int userId) {
        if (!likes.contains(userId)){
            throw new NoSuchUserException("Такой пользователь не ставил лайк");
        }
        likes.remove(id);
    }

}
