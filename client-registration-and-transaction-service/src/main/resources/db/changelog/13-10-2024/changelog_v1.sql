CREATE TABLE IF NOT EXISTS client (
    id SERIAL NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    middle_name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id          SERIAL NOT NULL,
    client_id   BIGINT NOT NULL,
    type        VARCHAR(20) NOT NULL,
    balance     DECIMAL(19, 2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE transaction
(
    id        SERIAL NOT NULL,
    amount    DECIMAL(19, 2),
    account_id BIGINT,
    is_error BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) references account(id)
);

