DROP TABLE IF EXISTS payment_analysis;
DROP TABLE IF EXISTS payments;

CREATE TABLE payments(
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    amount DECIMAL NOT NULL,
    order_id INT NOT NULL,
    currency VARCHAR(3) NOT NULL,
    email VARCHAR(1000) NOT NULL,

    PRIMARY KEY (id)
);

