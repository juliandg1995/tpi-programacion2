package dao;

import config.DatabaseConnection;
import entities.FichaBibliografica;
import entities.Libro;
import sql.LibroSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * DAO concreto para la entidad Libro. Implementa los métodos genéricos y
 * versiones que aceptan una Connection externa.
 */
public class LibroDao implements GenericDao<Libro> {

    private final FichaBibliograficaDao fichaDao = new FichaBibliograficaDao();

    @Override
    public void crear(Libro libro) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(libro, conn);
        }
    }

    public void crear(Libro libro, Connection conn) throws SQLException {

        String sql = LibroSQL.INSERT;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, libro.getEliminado() != null ? libro.getEliminado() : false);
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            if (libro.getAnioEdicion() != null) {
                ps.setInt(5, libro.getAnioEdicion());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    long id = rs.getLong(1);
                    libro.setId(id);
                }
            }
        }
    }

    @Override
    public Libro leer(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerConFicha(id, conn);
        }
    }

    public Libro leerConFicha(long id, Connection conn) throws SQLException {

        String sql = LibroSQL.SELECT_BY_ID;

        Libro libro = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                   libro = mapearLibro(rs);
                }
            }
        }
        if (libro != null) {
            FichaBibliografica ficha = fichaDao.leer(id, conn);
            libro.setFichaBibliografica(ficha);
        }
        return libro;
    }

    @Override
    public List<Libro> leerTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {

            String sql = LibroSQL.SELECT_ALL;

            try (PreparedStatement ps = conn.prepareStatement(sql); 
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Mapeo de los campos de libro
                    Libro libro = mapearLibro(rs);
                    long id = libro.getId();
                    
                    // Carga opcional de la ficha (lazy simple)
                    FichaBibliografica ficha = fichaDao.leer(id, conn);
                    libro.setFichaBibliografica(ficha);
                    libros.add(libro);
                }
            }
        }
        return libros;
    }

    @Override
    public void actualizar(Libro libro) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(libro, conn);
        }
    }

    public void actualizar(Libro libro, Connection conn) throws SQLException {

        String sql = LibroSQL.UPDATE;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            completarSetLibro(libro, ps, 1);
            ps.setLong(6, libro.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminar(id, conn);
        }
    }

    public void eliminar(long id, Connection conn) throws SQLException {

        String sql = LibroSQL.DELETE_LOGICO;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Búsqueda por campo relevante: ISBN (en la ficha). Realiza un join entre
     * libro y ficha_bibliografica.
     */
    public Libro buscarPorIsbn(String isbn) throws SQLException {

        String sql = LibroSQL.SELECT_BY_ISBN;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearLibro(rs);
                }
            }
        }
        return null;
    }

    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getLong("id"));
        libro.setEliminado(rs.getBoolean("eliminado"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setEditorial(rs.getString("editorial"));
        
        int anio = rs.getInt("anio_edicion");
        if (!rs.wasNull()) {
            libro.setAnioEdicion(anio);
        }
        return libro;
    }

    // dentro de FichaBibliograficaDao
    private void completarSetLibro(Libro libro, PreparedStatement ps, int offset) throws SQLException {
        // offset es la posición inicial de los campos comunes
        ps.setBoolean(offset, libro.getEliminado() != null ? libro.getEliminado() : false);
        ps.setString(offset+ 1, libro.getTitulo());
        ps.setString(offset + 2, libro.getAutor());
        ps.setString(offset + 3, libro.getEditorial());
        if (libro.getAnioEdicion() != null) {
            ps.setInt(offset + 4, libro.getAnioEdicion());
        } else {
            ps.setNull(offset + 4, Types.INTEGER);
        }
    }

}
