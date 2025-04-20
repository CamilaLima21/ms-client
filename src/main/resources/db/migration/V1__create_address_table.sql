CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    number VARCHAR(20) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    complement VARCHAR(100)
);
