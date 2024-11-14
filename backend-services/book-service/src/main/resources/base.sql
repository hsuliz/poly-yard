CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    published_date INT NOT NULL,
    pages INT NOT NULL,
    image TEXT,
    CONSTRAINT isbn_unique UNIQUE (isbn)
);