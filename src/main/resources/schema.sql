CREATE TABLE IF NOT EXISTS `PRODUCTS` (
            `code`  VARCHAR PRIMARY KEY,
            `name`  VARCHAR(50) NOT NULL,
            `price` FLOAT NOT NULL
    );