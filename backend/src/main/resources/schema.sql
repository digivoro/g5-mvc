DROP TABLE IF EXISTS sismo,suscripcion;

CREATE TABLE sismo (
id INT AUTO_INCREMENT PRIMARY KEY,
localidad varchar(255) NOT NULL,
fecha TIMESTAMP(9),
profundidad DECIMAL,
magnitud DECIMAL
);

CREATE TABLE suscripcion (
id INT AUTO_INCREMENT PRIMARY KEY,
email varchar(255) NOT NULL,
localidad varchar(255) NOT NULL,
nombre varchar(255) NOT NULL,
activo BOOLEAN DEFAULT 1
);

CREATE TABLE localidad (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre varchar(255) NOT NULL
)