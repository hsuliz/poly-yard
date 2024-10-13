CREATE TABLE users
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE Books
(
    id             SERIAL PRIMARY KEY,
    isbn           CHAR(13)     NOT NULL,
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    published_date bigint       NOT NULL,
    pages          INT          NOT NULL,
    image          VARCHAR(255) NOT NULL
);


CREATE TABLE reviews
(
    id         SERIAL PRIMARY KEY,
    rating     smallint NOT NULL,
    comment    VARCHAR(255),
    user_id    INT REFERENCES users (id) ON DELETE CASCADE,
    book_id    INT REFERENCES books (id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);




