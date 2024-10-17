DROP TABLE IF EXISTS sismo,suscripcion;

CREATE TABLE localidad (
id INT AUTO_INCREMENT PRIMARY KEY,
nombre varchar(255) NOT NULL
);

CREATE TABLE sismo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    localidad_id INT,  -- Relación con la tabla de localidad
    localidad varchar(255) NOT NULL,  -- Nombre de la localidad para referencia rápida
    fecha TIMESTAMP(9),
    profundidad DECIMAL,
    magnitud DECIMAL,
    CONSTRAINT fk_localidad
        FOREIGN KEY (localidad_id) REFERENCES localidad(id)  -- Llave foránea
);

CREATE TABLE suscripcion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email varchar(255) NOT NULL,
    localidad_id INT,
    nombre varchar(255) NOT NULL,
    activo BOOLEAN DEFAULT 1,
    FOREIGN KEY (localidad_id) REFERENCES localidad(id)
);
