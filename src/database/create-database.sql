/* 
 * Author:  Julian Daniel Gómez <https://github.com/juliandg1995>
 * Created: 16 nov 2025
 */

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_tfi
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE biblioteca_tfi;

-- Tabla LIBRO 
CREATE TABLE IF NOT EXISTS libro (
  id BIGINT NOT NULL AUTO_INCREMENT,
  eliminado TINYINT(1) NOT NULL DEFAULT 0,
  titulo VARCHAR(150) NOT NULL,
  autor VARCHAR(120) NOT NULL,
  editorial VARCHAR(100),
  anio_edicion INT,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla FICHA_BIBLIOGRAFICA 
-- Clave primaria compartida: id es PK y FK a libro(id)
CREATE TABLE IF NOT EXISTS ficha_bibliografica (
  id BIGINT NOT NULL,
  eliminado TINYINT(1) NOT NULL DEFAULT 0,
  isbn VARCHAR(17) UNIQUE,
  clasificacion_dewey VARCHAR(20),
  estanteria VARCHAR(20),
  idioma VARCHAR(30),
  PRIMARY KEY (id),
  CONSTRAINT fk_ficha_libro
    FOREIGN KEY (id)
    REFERENCES libro(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;


-- Datos de prueba para LIBRO
INSERT INTO libro (eliminado, titulo, autor, editorial, anio_edicion)
VALUES
  (0, 'El señor de los anillos', 'J. R. R. Tolkien', 'Minotauro', 2002),
  (0, 'Cien años de soledad', 'Gabriel García Márquez', 'Sudamericana', 1985),
  (0, 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 2008);

-- Datos de prueba para FICHA_BIBLIOGRAFICA
-- Se usan los mismos id de los libros insertados arriba
INSERT INTO ficha_bibliografica (id, eliminado, isbn, clasificacion_dewey, estanteria, idioma)
VALUES
  (1, 0, '978-84-450-7562-8', '823', 'F1', 'ESPAÑOL'),
  (2, 0, '978-84-376-0494-7', '863', 'F2', 'ESPAÑOL'),
  (3, 0, '978-0-13-235088-4', '005.1', 'INF1', 'INGLES');