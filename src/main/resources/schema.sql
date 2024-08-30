DROP TABLE IF EXISTS sismo;
CREATE TABLE sismo (
id INT AUTO_INCREMENT PRIMARY KEY,
localidad varchar(255) NOT NULL,
fecha TIMESTAMP(9),
profundidad DECIMAL,
magnitud DECIMAL
);