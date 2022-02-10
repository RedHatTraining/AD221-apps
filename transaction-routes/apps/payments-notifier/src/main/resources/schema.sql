DROP TABLE IF EXISTS payments;

CREATE TABLE payments(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL NOT NULL,
    order_id INT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    email VARCHAR(1000) NULL,
    notified TINYINT DEFAULT 1,
    PRIMARY KEY (id)
);
