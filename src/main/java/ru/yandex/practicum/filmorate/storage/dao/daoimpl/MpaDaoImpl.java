package ru.yandex.practicum.filmorate.storage.dao.daoimpl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchMpaException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.MpaDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MpaDaoImpl implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Mpa> getMpaById(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from MPA_RATING where MPA_RATING_ID = ?", id);
        if (userRows.next()) {
            Mpa mpa = new Mpa(userRows.getInt("MPA_RATING_ID"), userRows.getString("MPA_RATING"));

            log.info("Найден рейтинг: {} {}", mpa.getId(), mpa.getName());

            return Optional.of(mpa);
        } else {
            log.info("Рейтинг с идентификатором {} не найден.", id);
            throw new NoSuchMpaException("Рейтинг с идентификатором {} не найден.");
        }

    }

    @Override
    public List<Mpa> getMPAs() {
        String sql = "select * from MPA_RATING";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeMPA(rs));
    }


    private Mpa makeMPA(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("MPA_RATING_ID");
        String name = rs.getString("MPA_RATING");
        return new Mpa(id, name);
    }
}
