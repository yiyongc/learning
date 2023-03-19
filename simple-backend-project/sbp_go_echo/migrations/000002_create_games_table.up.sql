CREATE TABLE IF NOT EXISTS games (
    id serial PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    published_date date NOT NULL,
    inventory_count int NOT NULL,
    created_date TIMESTAMP NOT NULL DEFAULT NOW(),
    publisher_id serial,
    CONSTRAINT fk_publisher
        FOREIGN KEY(publisher_id)
            REFERENCES publishers(id) ON DELETE CASCADE
);