INSERT INTO localidad (nombre) VALUES ('Quillota');
INSERT INTO localidad (nombre) VALUES ('San Pedro de Atacama');
INSERT INTO localidad (nombre) VALUES ('San Antonio de los Cobres');
INSERT INTO localidad (nombre) VALUES ('Copiapó');
INSERT INTO localidad (nombre) VALUES ('Visviri');
INSERT INTO localidad (nombre) VALUES ('Pica');
INSERT INTO localidad (nombre) VALUES ('Mina Collahuasi');
INSERT INTO localidad (nombre) VALUES ('Socaire');
INSERT INTO localidad (nombre) VALUES ('Constitución');
INSERT INTO localidad (nombre) VALUES ('Calama');
INSERT INTO sismo (localidad, localidad_id, fecha, profundidad, magnitud) VALUES ('Quillota', 1, '2024-08-30', 112, 3.1);
INSERT INTO sismo (localidad, localidad_id, fecha, profundidad, magnitud) VALUES ('Copiapó', 4, '2024-10-09', 45, 3.7);
INSERT INTO sismo (localidad, localidad_id, fecha, profundidad, magnitud) VALUES ('Pica', 6, '2024-10-04', 20, 6.6);
INSERT INTO sismo (localidad, localidad_id, fecha, profundidad, magnitud) VALUES ('Calama', 10, '2024-08-25', 100, 9.1);
INSERT INTO suscripcion (email, localidad_id, nombre) VALUES ('xelocandia@gmail.com', 1, 'Marcelo');
