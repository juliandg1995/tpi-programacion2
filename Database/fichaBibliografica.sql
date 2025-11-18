/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template

 * Author:  Julian Daniel Gómez <https://github.com/juliandg1995>
 * Created: 15 nov 2025
 */

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_tpi
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE biblioteca_tpi;

-- Tabla LIBRO (A)
CREATE TABLE IF NOT EXISTS libro (
  id BIGINT NOT NULL AUTO_INCREMENT,
  eliminado TINYINT(1) NOT NULL DEFAULT 0,
  titulo VARCHAR(150) NOT NULL,
  autor VARCHAR(120) NOT NULL,
  editorial VARCHAR(100),
  anio_edicion INT,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla FICHA_BIBLIOGRAFICA (B)
-- Clave primaria: id es PK y FK a libro(id)
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

