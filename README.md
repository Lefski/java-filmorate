# java-filmorate
[link to ER-model](https://dbdiagram.io/d/64ac3b0202bd1c4a5ed1860b)
![Screenshot of a ER-model](https://github.com/Lefski/java-filmorate/blob/main/ER_model.png)

1. Получение всех фильмов
```sql
SELECT *
FROM films;
```

2. Получение всех пользователей
```sql
SELECT *
FROM users;
```

3. Получение N наиболее популярных фильмов
```sql
SELECT *
FROM films
ORDER BY film_id DESC
LIMIT N;
```
4. Получение пользователя по id (id = 1)
```sql
SELECT *
FROM users
WHERE user_id = 1;
```
5. Получение списка общих друзей одного пользователя (id = 1) с другим пользователем(id = 2) 
```sql
SELECT u.*
FROM users u
JOIN friendships f1 ON f1.user_id_1 = u.user_id
JOIN friendships f2 ON f2.user_id_2 = u.user_id
WHERE f1.user_id_2 = 1 OR f2.user_id_1 = 2;
```
6. Получение списка друзей одного пользователя c id = 1
```sql
SELECT u.*
FROM users u
JOIN friendships f ON (f.user_id_1 = u.user_id)
WHERE f.user_id_1 = 1 
```
7. Получение всех фильмов определенного жанра
```sql
SELECT *
FROM films f
JOIN genre g ON f.genre_id = g.genre_id
WHERE g.name = 'Ужасы';
```
