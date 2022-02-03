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


CREATE TABLE payment_analysis(
    id INT NOT NULL AUTO_INCREMENT,
    payment_id INT NOT NULL,
    analysis_status VARCHAR(50) DEFAULT 'Pending',
    fraud_score DECIMAL(4,3),

    PRIMARY KEY (id),
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);
