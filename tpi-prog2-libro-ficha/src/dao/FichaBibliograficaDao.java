/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import entities.FichaBibliografica;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sandra Martinez
 */
public class FichaBibliograficaDao implements GenericDao<FichaBibliografica> {
    
    private static final String SELECT_BY_ISBN_SQL = 
        "SELECT id, eliminado, isbn, clasificacionDewey, estanteria, idioma FROM FichaBibliografica WHERE isbn = ? AND eliminado = FALSE";
    
    private static final String INSERT_SQL = 
        "INSERT INTO FichaBibliografica (isbn, clasificacionDewey, estanteria, idioma, eliminado, libro_id) " +
        "VALUES (?, ?, ?, ?, ?, ?)";
    
    public FichaBibliograficaDao() {
    }

    private FichaBibliografica mapResultSetToFicha(ResultSet rs) throws SQLException {
        FichaBibliografica ficha = new FichaBibliografica();
        ficha.setId(rs.getLong("id"));
        ficha.setEliminado(rs.getBoolean("eliminado"));
        ficha.setIsbn(rs.getString("isbn"));
        ficha.setClasificacionDewey(rs.getString("clasificacionDewey"));
        ficha.setEstanteria(rs.getString("estanteria"));
        ficha.setIdioma(rs.getString("idioma"));
        return ficha;
    }

    public FichaBibliografica crearTransaccional(FichaBibliografica ficha, long libroId, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, ficha.getIsbn());
            stmt.setString(2, ficha.getClasificacionDewey());
            stmt.setString(3, ficha.getEstanteria());
            stmt.setString(4, ficha.getIdioma());
            stmt.setBoolean(5, ficha.getEliminado());
            stmt.setLong(6, libroId); 
            
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la Ficha falló, no se insertó ninguna fila.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ficha.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("La creación de la Ficha falló, no se obtuvo el ID.");
                }
            }
        }
        return ficha;
    }

    public FichaBibliografica buscarPorISBN(String isbn) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ISBN_SQL)) {
            
            stmt.setString(1, isbn);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFicha(rs);
                }
            }
        }
        return null;
    }
    
    @Override public FichaBibliografica crear(FichaBibliografica entity) throws SQLException { throw new UnsupportedOperationException("Use el metodo crearTransaccional."); }
    @Override public FichaBibliografica leer(long id) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public List<FichaBibliografica> leerTodos() throws SQLException { return new ArrayList<>(); }
    @Override public void actualizar(FichaBibliografica entity) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public void eliminar(long id) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public FichaBibliografica crear(FichaBibliografica entity, Connection conn) throws SQLException { throw new UnsupportedOperationException("Use el metodo crearTransaccional."); }
    @Override public void actualizar(FichaBibliografica entity, Connection conn) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
    @Override public void eliminar(long id, Connection conn) throws SQLException { throw new UnsupportedOperationException("Not supported yet."); }
}