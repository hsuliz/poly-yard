CREATE TYPE review_type AS ENUM ('BOOK', 'ALBUM');
CREATE TYPE resource_type AS ENUM ('ISBN', 'ISRC', 'UPC');

CREATE TABLE resources (
    id SERIAL PRIMARY KEY,
    type resource_type NOT NULL,
    value VARCHAR(255) NOT NULL
);

CREATE TABLE reviews (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    type review_type NOT NULL,
    resource_id BIGINT NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    created_at TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (resource_id) REFERENCES resources (id) ON DELETE CASCADE
);