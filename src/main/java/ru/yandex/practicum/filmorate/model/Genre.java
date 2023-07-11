package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Genre {
    Integer id;
    String name;

    public Genre() {
    }

    public Genre(Integer id) {
        this.id = id;
    }

    public Genre(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
