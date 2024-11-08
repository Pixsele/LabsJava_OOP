CREATE SCHEMA if not exists public;

-- Создание таблицы функций
CREATE TABLE functions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    x_to FLOAT NOT NULL,
    x_from FLOAT NOT NULL,
    count INT NOT NULL
);

-- Создание таблицы точек
CREATE TABLE points (
    id SERIAL PRIMARY KEY,
    function INT NOT NULL,
    x FLOAT NOT NULL,
    y FLOAT NOT NULL,
    FOREIGN KEY (function) REFERENCES functions(id) ON DELETE CASCADE
);
