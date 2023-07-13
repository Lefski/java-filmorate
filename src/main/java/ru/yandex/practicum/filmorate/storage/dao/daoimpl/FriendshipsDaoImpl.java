package ru.yandex.practicum.filmorate.storage.dao.daoimpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchFriendShipException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.storage.dao.FriendshipsDao;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class FriendshipsDaoImpl implements FriendshipsDao {

    private final UserDbStorage userDbStorage;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Integer> getFriendsListByUserId(Integer id) {
        checkId(id);
        String sql = "SELECT USER_ID_2 FROM FRIENDSHIPS WHERE USER_ID_1 = ? AND FRIENDSHIP_STATUS_ID <> 2";
        return jdbcTemplate.queryForList(sql, Integer.class, id);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        checkId(userId);
        checkId(friendId);
        if (!getFriendsListByUserId(friendId).contains(friendId)) {

            String sqlQuery = "insert into FRIENDSHIPS(USER_ID_1, USER_ID_2, FRIENDSHIP_STATUS_ID) " + "values (?, ?, ?)";
            jdbcTemplate.update(sqlQuery, userId, friendId, 1);
        } else
            throw new ValidationException("Friendship of user " + userId + " and user " + friendId + " already exists");
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        checkId(userId);
        checkId(friendId);

        if (getFriendsListByUserId(userId).contains(friendId)) {
            String sqlQuery = "delete from FRIENDSHIPS where USER_ID_1 = ? AND USER_ID_2 = ?";
            jdbcTemplate.update(sqlQuery, userId, friendId);
        } else throw new NoSuchFriendShipException("Переданного друга нет в списке друзей");
    }

    private void checkId(Integer id) {
        userDbStorage.getUserById(id);
    }
}
