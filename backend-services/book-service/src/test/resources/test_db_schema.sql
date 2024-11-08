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
