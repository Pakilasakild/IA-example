-- Duomenų bazės sukūrimo skriptas
-- Paleiskite šį skriptą MySQL serveryje

-- Sukurti duomenų bazę
CREATE DATABASE IF NOT EXISTS ia_database;
USE ia_database;

-- Pavyzdinė lentelė (galite ištrinti ir sukurti savo)
CREATE TABLE IF NOT EXISTS example_table (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Pavyzdiniai duomenys
INSERT INTO example_table (name, description) VALUES
('Pavyzdys 1', 'Tai yra pirmasis pavyzdys'),
('Pavyzdys 2', 'Tai yra antrasis pavyzdys');

-- Patikrinti duomenis
SELECT * FROM example_table;

