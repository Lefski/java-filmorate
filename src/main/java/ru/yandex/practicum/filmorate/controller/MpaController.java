package ru.yandex.practicum.filmorate.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.daoimpl.MpaDaoImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class MpaController {

    private final MpaDaoImpl mpaDao;

    @GetMapping
    public List<Mpa> getMpa() {
        log.info("Получен запроc на получение списка всех MPA");
        return mpaDao.getMPAs();
    }

    @GetMapping("/{id}")
    public Optional<Mpa> getMpaById(@PathVariable int id) {
        log.info("Получен запроc на поиск MPA по id");
        return mpaDao.getMpaById(id);
    }

}
