package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MpaService {

    private final MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    public List<Mpa> getMpa() {
        log.info("Выполнен запроc на получение списка всех MPA");
        return mpaDao.getMPAs();
    }

    public Optional<Mpa> getMpaById(@PathVariable int id) {
        log.info("Выполнен запроc на поиск MPA по id");
        return mpaDao.getMpaById(id);
    }
}
