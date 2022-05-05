DROP TABLE IF EXISTS movies;

CREATE TABLE IF NOT EXISTS movies (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200),
    director VARCHAR(200),
    year INT
)

INSERT INTO movies VALUES (1, "The Big Lebowski", "Ethan Cohen", 1998)