CREATE TABLE IF NOT EXISTS users
(
    user_id  integer PRIMARY KEY,
    email    varchar,
    login    varchar,
    name     varchar,
    birthday date
);

CREATE TABLE IF NOT EXISTS friendships
(
    user_id_1            integer,
    user_id_2            integer,
    friendship_status_id integer,
    PRIMARY KEY (user_id_1, user_id_2)
);

CREATE TABLE IF NOT EXISTS friendship_status
(
    friendship_status_id integer PRIMARY KEY,
    status               varchar
);

CREATE TABLE IF NOT EXISTS films
(
    film_id       integer PRIMARY KEY,
    name          varchar,
    description   varchar,
    releaseDate   date,
    duration      integer,
    genre_id      integer,
    mpa_rating_id integer
);

CREATE TABLE IF NOT EXISTS genre
(
    genre_id integer PRIMARY KEY,
    name     varchar
);

CREATE TABLE IF NOT EXISTS film_likes
(
    film_id integer PRIMARY KEY,
    user_id integer
);

CREATE TABLE IF NOT EXISTS mpa_rating
(
    mpa_rating_id integer PRIMARY KEY,
    mpa_rating    varchar
);

ALTER TABLE friendships
    ADD FOREIGN KEY (user_id_1) REFERENCES users (user_id);

ALTER TABLE friendships
    ADD FOREIGN KEY (user_id_2) REFERENCES users (user_id);

ALTER TABLE film_likes
    ADD FOREIGN KEY (film_id) REFERENCES films (film_id);

ALTER TABLE film_likes
    ADD FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE films
    ADD FOREIGN KEY (genre_id) REFERENCES genre (genre_id);

ALTER TABLE films
    ADD FOREIGN KEY (mpa_rating_id) REFERENCES mpa_rating (mpa_rating_id);

ALTER TABLE friendships
    ADD FOREIGN KEY (friendship_status_id) REFERENCES friendship_status (friendship_status_id);


