CREATE TABLE IF NOT EXISTS person (
     id BIGSERIAL PRIMARY KEY,
     identification VARCHAR(255) NOT NULL UNIQUE,
     name VARCHAR(255) NOT NULL,
     gender VARCHAR(50),
     age INT,
     address VARCHAR(255),
     phone VARCHAR(50),
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer (
     id BIGINT PRIMARY KEY,
     password VARCHAR(255) NOT NULL,
     membership_number VARCHAR(255) UNIQUE,
     status BOOLEAN NOT NULL,
     created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(255) NOT NULL UNIQUE,
    account_type VARCHAR(255) NOT NULL,
    status BOOLEAN NOT NULL,
    customer_id BIGINT,
    current_balance DECIMAL(19, 2) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS movement (
    id BIGSERIAL PRIMARY KEY,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    origin_account_id BIGINT NOT NULL,
    initial_balance DECIMAL(19, 2) NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    movement_type VARCHAR(255) NOT NULL,
    movement_status BOOLEAN NOT NULL,
    message VARCHAR(255) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (origin_account_id) REFERENCES account(id) ON DELETE SET NULL
);

DELETE FROM person WHERE true;
DELETE FROM customer WHERE true;
DELETE FROM account WHERE true;

INSERT INTO person (identification, name, gender, age, address, phone) VALUES
('1234567890', 'Juan Pérez', 'Masculino', 35, 'Calle Principal 123, Ciudad', '555-1234'),
('0987654321', 'María López', 'Femenino', 28, 'Avenida Central 456, Ciudad', '555-5678'),
('1122334455', 'Carlos Rodríguez', 'Masculino', 42, 'Plaza Mayor 789, Ciudad', '555-9012');

INSERT INTO customer (id, password, membership_number, status) VALUES
(1, 'password123', 'MEM001', true),
(2, 'securepass', 'MEM002', true),
(3, 'strongpwd', 'MEM003', false);

INSERT INTO account (account_number, account_type, status, customer_id, current_balance) VALUES
('ACC001', 'SAVINGS', true, 1, 5000.00),
('ACC002', 'CHECKING', true, 1, 2500.50),
('ACC003', 'SAVINGS', true, 2, 10000.75),
('ACC004', 'CHECKING', false, 3, 0.00);


