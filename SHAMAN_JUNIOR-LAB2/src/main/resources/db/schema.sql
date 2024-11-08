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

-- Создание таблицы процессов
CREATE TABLE processes (
    id SERIAL PRIMARY KEY,
    function INT NOT NULL,
    type VARCHAR(50) NOT NULL,  -- Отдельно хранится тип процесса
    start_time TIMESTAMP NOT NULL,  -- Время начала
    end_time TIMESTAMP NOT NULL,      -- Время конца
    FOREIGN KEY (function) REFERENCES functions(id) ON DELETE CASCADE
);

-- Создание таблицы результата процессов
CREATE TABLE process_results (
    id SERIAL PRIMARY KEY,
    process_id INT NOT NULL,
    result FLOAT NOT NULL,
    FOREIGN KEY (processId) REFERENCES processes(id) ON DELETE CASCADE
);
