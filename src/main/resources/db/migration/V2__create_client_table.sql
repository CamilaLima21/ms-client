CREATE TABLE client (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    birth DATE NOT NULL,
    address_id BIGINT NOT NULL,
    CONSTRAINT fk_client_address FOREIGN KEY (address_id)
        REFERENCES address(id) ON DELETE CASCADE,
    CONSTRAINT uq_client_cpf UNIQUE (cpf),
    CONSTRAINT uq_client_email UNIQUE (email)
);
