create table if not exists users
(
    id       SERIAL NOT NULL,
    login    VARCHAR(20),
    password VARCHAR(120),
    PRIMARY KEY (id)
);