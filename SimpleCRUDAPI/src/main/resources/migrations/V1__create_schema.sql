CREATE TABLE orders (
    id BIGINT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
INSERT INTO orders (id, user_id, product_name, quantity) VALUES (1, 1, 'Laptop', 2);