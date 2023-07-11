package ru.yandex.practicum.filmorate.model;


import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MpaRating {
    Integer id;
    String name;

    public MpaRating(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public MpaRating() {
    }
}
