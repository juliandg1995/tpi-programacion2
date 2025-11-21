# 📚 **Sistema de Gestión de Biblioteca**
### **Trabajo Práctico Integrador – Programación II**
**Proyecto:** _TPI-Grupo85-FichaBibliografica_

🔗 **Video explicativo:**  
https://drive.google.com/drive/u/0/folders/1n0amCWXpS4E-veY1utqqBI0Tn3yaiYQk

---

## 1. 📝 Descripción General
Este proyecto implementa un sistema **CRUD completo** para la gestión de **Libros** y sus **Fichas Bibliográficas**, vinculadas mediante una **relación 1→1 con clave primaria compartida**.

Incluye:

- Gestión de base de datos **MySQL**
- **Validaciones de negocio**
- **Transacciones** para operaciones atómicas
- Menú de consola interactivo
- Arquitectura por capas:  
  **Entities → DAO → Service → Main**

---

## 2. ⚙️ Requisitos Previos

**Software necesario:**
- **Java 21**
- **MySQL 8.x**
- IDE sugerido: **IntelliJ / NetBeans / Eclipse**

**Archivo de configuración `db.properties`:**

```
db.url=jdbc:mysql://localhost:3306/biblioteca_tpi
db.user=root
db.password=
```

---

## 3. 🗄️ Script SQL – Estructura de la Base de Datos

```sql
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
```

---

## 4. 🧱 Arquitectura del Proyecto

```text
TPI-Grupo85-FichaBibliografica/
├─ src/
│  ├─ config/
│  │  └─ DatabaseConnection.java
│  │
│  ├─ dao/
│  │  ├─ GenericDao.java
│  │  ├─ FichaBibliograficaDao.java
│  │  └─ LibroDao.java
│  │
│  ├─ entities/
│  │  ├─ FichaBibliografica.java
│  │  └─ Libro.java
│  │
│  ├─ main/
│  │  ├─ Main.java
│  │  └─ AppMenu.java
│  │
│  ├─ service/
│  │  ├─ GenericService.java
│  │  ├─ LibroService.java
│  │  ├─ LibroServiceImpl.java
│  │  ├─ FichaBibliograficaService.java
│  │  ├─ FichaBibliograficaServiceImpl.java
│  │  └─ validations/
│  │     └─ ValidacionService.java
│  │
│  ├─ sql/
│  │  ├─ LibroSQL.java
│  │  └─ FichaBibliograficaSQL.java
│  │
│  └─ db.properties
│
└─ test/
   └─ test/
      ├─ TestAppMenuEstructura.java
      ├─ TestDatabaseConfig.java
      ├─ TestLimpiezaBD.java
      ├─ TestService.java
      ├─ TestSinDependenciasCirculares.java
      └─ TestTransaccionSimple.java
```

---

## 5. 🧩 Funcionalidades del Sistema

- Crear Libro junto con su Ficha Bibliográfica mediante transacción atómica.
- Listar todos los libros con sus fichas (si existen).
- Buscar libro por ID.
- Actualizar libro.
- Eliminación lógica del libro.
- Búsqueda por ISBN.
- Búsqueda por título.

---

## 6. 👥 Equipo de Trabajo (Roles)

**Sandra Débora Martínez**  
- Entidades Libro y FichaBibliografica  
- Presentación teórica del módulo DAO  

**Melisa Inés Martellini**  
- Diagrama UML  
- Scripts SQL (CREATE TABLE, FK, UNIQUE, relación 1→1)  
- Implementación de DatabaseConnection  

**Fabricio Nicolás Puccio**  
- Implementación de la capa Service  
- Manejo de transacciones  
- Lógica de negocio  

**Julián Daniel Gómez**  
- Implementación completa del módulo DAO  
- Desarrollo del AppMenu  
- Pruebas y validaciones finales  

---

## 7. ▶️ Ejecución

Desde el IDE ejecutar:

```
main/AppMenu.java
```

Aparecerá el menú interactivo con todas las operaciones CRUD disponibles.

---

## 8. 📝 Notas Finales

- Se utilizan **PreparedStatement** para prevenir SQL Injection.  
- `DatabaseConnection` usa archivo externo de configuración.  
- La relación Libro ↔ FichaBibliografica respeta la **clave primaria compartida**.  
- El proyecto sigue la arquitectura solicitada por la cátedra.  

---

🎯 **Fin del README**
