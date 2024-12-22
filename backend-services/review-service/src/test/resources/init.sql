CREATE TYPE review_type AS ENUM ('BOOK', 'ALBUM');
CREATE TYPE resource_type AS ENUM ('ISBN', 'ISRC', 'UPC');

CREATE TABLE resources (
    id SERIAL PRIMARY KEY,
    type resource_type NOT NULL,
    value VARCHAR(255) NOT NULL
);

INSERT INTO resources (type, value) VALUES
('ISBN', '9783161484100'),
('ISBN', '9780306406157'),
('ISBN', '9781451673319'),
('ISBN', '9780140449136');

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

INSERT INTO reviews (username, type, resource_id, rating, comment) VALUES
('user1', 'BOOK', 1, 5, 'Fantastic book! A must-read.'),
('user2', 'BOOK', 2, 4, 'Great concepts but a bit verbose.'),
('user3', 'BOOK', 3, 5, 'Incredible storytelling.'),
('user4', 'BOOK', 4, 3, 'Good, but not my style.');
