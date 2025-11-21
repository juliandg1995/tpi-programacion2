README - Sistema de Gestión de Biblioteca
Trabajo Práctico Integrador – Programación II
Proyecto: TPI-Grupo85-FichaBibliografica

ENLACE VIDEO EXPLICATIVO:https://drive.google.com/drive/u/0/folders/1n0amCWXpS4E-veY1utqqBI0Tn3yaiYQk

------------------------------------------------------------
1. Descripción General
------------------------------------------------------------
Este proyecto implementa un sistema CRUD completo para la gestión de Libros y sus Fichas Bibliográficas asociadas mediante una relación 1→1 con clave primaria compartida.

El sistema incluye:
- Manejo de base de datos MySQL
- Validaciones de negocio
- Operaciones atómicas (transacciones)
- Menú de consola interactivo
- Arquitectura por capas: Entities → DAO → Service → Main

------------------------------------------------------------
2. Requisitos Previos
------------------------------------------------------------
Software necesario:
- Java 21 (recomendado por la cátedra)
- MySQL 8.x
- IDE sugerido: IntelliJ / NetBeans / Eclipse

Archivo db.properties (ubicado en src/main/resources):

db.url=jdbc:mysql://localhost:3306/biblioteca_tpi
db.user=root
db.password=

------------------------------------------------------------
3. Script SQL (Estructura de la Base de Datos)
------------------------------------------------------------
CREATE DATABASE IF NOT EXISTS biblioteca_tpi
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE biblioteca_tpi;

CREATE TABLE libro (
  id BIGINT NOT NULL AUTO_INCREMENT,
  eliminado TINYINT(1) NOT NULL DEFAULT 0,
  titulo VARCHAR(150) NOT NULL,
  autor VARCHAR(120) NOT NULL,
  editorial VARCHAR(100),
  anio_edicion INT,
  PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE ficha_bibliografica (
  id BIGINT NOT NULL,
  eliminado TINYINT(1) NOT NULL DEFAULT 0,
  isbn VARCHAR(17) UNIQUE,
  clasificacion_dewey VARCHAR(20),
  estanteria VARCHAR(20),
  idioma VARCHAR(30),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES libro(id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB;

------------------------------------------------------------
4. Arquitectura del Proyecto
------------------------------------------------------------
src/
 main/java/
   entities/
     Libro.java
     FichaBibliografica.java
   dao/
     LibroDao.java
     FichaBibliograficaDao.java
     GenericDao.java
   service/
     LibroService.java
     LibroServiceImpl.java
     FichaBibliograficaService.java
     FichaBibliograficaServiceImpl.java
     validations/ValidacionService.java
   config/
     DatabaseConnection.java
   sql/
     LibroSQL.java
     FichaBibliograficaSQL.java
   main/
     AppMenu.java

 resources/
   db.properties

------------------------------------------------------------
5. Funcionalidades del Sistema
------------------------------------------------------------
- Crear Libro junto con su Ficha Bibliográfica en una transacción atómica.
- Listar todos los libros con sus fichas (si existen).
- Buscar libro por ID (función en desarrollo).
- Actualizar libro (función en desarrollo).
- Eliminación lógica de libro.
- Búsqueda por ISBN (función en desarrollo).
- Búsqueda por título (función en desarrollo).

------------------------------------------------------------
6. Equipo de Trabajo (roles)
------------------------------------------------------------
Sandra Débora Martínez
  - Implementación de las entidades Libro y FichaBibliografica
  - Presentación teórica del módulo DAO

Melisa Inés Martellini
  - Diseño del Diagrama UML de Clases
  - Generación de los scripts SQL (CREATE TABLE, FK, UNIQUE, relación 1→1)
  - Implementación de DatabaseConnection (config/)

Fabricio Nicolás Puccio
  - Implementación de la capa Service
  - Manejo de transacciones en LibroServiceImpl
  - Desarrollo de la lógica de negocio

Julián Daniel Gómez
  - Implementación completa del módulo DAO
  - Desarrollo del menú principal (AppMenu)
  - Integración final del sistema y validaciones complementarias

------------------------------------------------------------
7. Ejecución
------------------------------------------------------------
Desde IntelliJ o NetBeans:
Ejecutar la clase:
main/AppMenu.java

El sistema abrirá un menú de consola con todas las opciones CRUD disponibles.

------------------------------------------------------------
8. Notas Finales
------------------------------------------------------------
- Se utilizaron PreparedStatement para prevenir SQL Injection.
- DatabaseConnection utiliza archivo externo de configuración.
- La relación Libro ↔ FichaBibliografica respeta la clave primaria compartida.
- El proyecto sigue estrictamente la arquitectura solicitada por la cátedra.

Fin del README
