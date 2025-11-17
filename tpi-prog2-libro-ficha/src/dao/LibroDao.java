/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.Libro;
import entities.FichaBibliografica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

/**
 *
 * @author Sandra Martinez
 */
 
public class LibroDao implements GenericDao<Libro> {

    private static final String SELECT_ALL_SQL = 
        "SELECT l.id, l.eliminado, l.titulo, l.autor, l.editorial, l.anioEdicion, " +
        "f.id AS ficha_id, f.isbn, f.clasificacionDewey, f.estanteria, f.idioma, f.eliminado AS ficha_eliminado " +
        "FROM Libro l " +
        "LEFT JOIN FichaBibliografica f ON l.id = f.libro_id " +
        "WHERE l.eliminado = FALSE";

    private static final String INSERT_SQL = 
        "INSERT INTO Libro (titulo, autor, editorial, anioEdicion, eliminado) " +
        "VALUES (?, ?, ?, ?, ?)";
        
    private static final String UPDATE_SQL = 
        "UPDATE Libro SET titulo = ?, autor = ?, editorial = ?, anioEdicion = ? " +
        "WHERE id = ? AND eliminado = FALSE";

    private static final String DELETE_SQL = 
        "UPDATE Libro SET eliminado = TRUE WHERE id = ? AND eliminado = FALSE";

    public LibroDao() {
    }

    private Libro mapResultSetToLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        
        libro.setId(rs.getLong("id"));
        libro.setEliminado(rs.getBoolean("eliminado"));
        
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setEditorial(rs.getString("editorial"));
        
        Integer anio = rs.getInt("anioEdicion");
        if (rs.wasNull()) {
            anio = null;
        }
        libro.setAnioEdicion(anio);
        
        if (rs.getObject("ficha_id") != null) {
            FichaBibliografica ficha = new FichaBibliografica();
            ficha.setId(rs.getLong("ficha_id"));
            ficha.setEliminado(rs.getBoolean("ficha_eliminado"));
            ficha.setIsbn(rs.getString("isbn"));
            ficha.setClasificacionDewey(rs.getString("clasificacionDewey"));
            ficha.setEstanteria(rs.getString("estanteria"));
            ficha.setIdioma(rs.getString("idioma"));
            
            libro.setFichaBibliografica(ficha);
        }
        
        return libro;
    }
    
    @Override
    public List<Libro> leerTodos() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }

    public List<Libro> leerTodos(Connection conn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        
        try (PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = mapResultSetToLibro(rs);
                libros.add(libro);
            }
        }
        return libros;
    }
    
    @Override
    public Libro leer(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leer(id, conn);
        }
    }

    public Libro leer(long id, Connection conn) throws SQLException {
        final String SELECT_BY_ID_SQL = SELECT_ALL_SQL + " AND l.id = ?;";
        Libro libro = null;

        try (PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setLong(1, id); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    libro = mapResultSetToLibro(rs);
                }
            }
        }
        return libro;
    }
    
    @Override
    public Libro crear(Libro entity) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return crear(entity, conn);
        }
    }

    @Override
    public Libro crear(Libro libro, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                INSERT_SQL, 
                PreparedStatement.RETURN_GENERATED_KEYS)) { 
            
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            
            if (libro.getAnioEdicion() != null) {
                stmt.setInt(4, java.sql.Types.INTEGER); // CORRECCIÓN: USAR Types.INTEGER
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            
            stmt.setBoolean(5, libro.getEliminado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación del libro falló, no se insertó ninguna fila.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    libro.setId(generatedKeys.getLong(1)); 
                } else {
                    throw new SQLException("La creación del libro falló, no se obtuvo el ID.");
                }
            }
        }
        return libro;
    }
    
    @Override
    public void actualizar(Libro entity) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(entity, conn);
        }
    }

    @Override
    public void actualizar(Libro libro, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(UPDATE_SQL)) {
            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getEditorial());
            
            if (libro.getAnioEdicion() != null) {
                stmt.setInt(4, java.sql.Types.INTEGER); // CORRECCIÓN: USAR Types.INTEGER
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            
            stmt.setLong(5, libro.getId()); 
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("La actualización del Libro falló. ID no encontrado o ya eliminado.");
            }
        }
    }

    @Override
    public void eliminar(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminar(id, conn);
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_SQL)) {
            stmt.setLong(1, id);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                 throw new SQLException("La eliminación lógica del Libro falló. ID no encontrado.");
            }
        }
    }
}