package dao;

import config.DatabaseConnection;
import entities.FichaBibliografica;
import sql.FichaBibliograficaSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * DAO concreto para la entidad FichaBibliografica.
 */
public class FichaBibliograficaDao implements GenericDao<FichaBibliografica> {

    @Override
    public void crear(FichaBibliografica ficha) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(ficha, conn);
        }
    }

    public void crear(FichaBibliografica ficha, Connection conn) throws SQLException {
        String sql = FichaBibliograficaSQL.INSERT;

        // Se utiliza prepareStatement como medida de seguridad para evitar inyección de SQL por medio de los parámetros
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Mapeo de campos de la entidad
            ps.setLong(1, ficha.getId());
            completarSetFicha(ficha, ps, 2);    // desde el parámetro 2 en adelante
            ps.executeUpdate();
        }
    }

    @Override
    public FichaBibliografica leer(long id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leer(id, conn);
        }
    }

    public FichaBibliografica leer(long id, Connection conn) throws SQLException {
        String sql = FichaBibliograficaSQL.SELECT_BY_ID;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Ficha con los parámetros mapeados
                   return mapearFicha(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<FichaBibliografica> leerTodos() throws SQLException {
        
        List<FichaBibliografica> lista = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            
            String sql = FichaBibliograficaSQL.SELECT_ALL;
            
            try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Lista de fichas con todos los campos mapeados
                    lista.add(mapearFicha(rs));
                }
            }
        }
        return lista;
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(ficha, conn);
        }
    }

    public void actualizar(FichaBibliografica ficha, Connection conn) throws SQLException {

        String sql = FichaBibliograficaSQL.UPDATE;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            completarSetFicha(ficha, ps, 1);    // desde el parámetro 1
            ps.setLong(6, ficha.getId());       // el id queda al final
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
        String sql = "UPDATE ficha_bibliografica SET eliminado = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    // Método de mapeo de campos utilizados en leer() y leerTodo()
    private FichaBibliografica mapearFicha(ResultSet rs) throws SQLException {
        FichaBibliografica ficha = new FichaBibliografica();
        ficha.setId(rs.getLong("id"));
        ficha.setEliminado(rs.getBoolean("eliminado"));
        ficha.setIsbn(rs.getString("isbn"));
        ficha.setClasificacionDewey(rs.getString("clasificacion_dewey"));
        ficha.setEstanteria(rs.getString("estanteria"));
        ficha.setIdioma(rs.getString("idioma"));
        return ficha;
    }

    // dentro de FichaBibliograficaDao
    private void completarSetFicha(FichaBibliografica ficha, PreparedStatement ps, int offset) throws SQLException {
        // offset es la posición inicial de los campos comunes
        ps.setBoolean(offset, ficha.getEliminado() != null ? ficha.getEliminado() : false);
        ps.setString(offset + 1, ficha.getIsbn());
        ps.setString(offset + 2, ficha.getClasificacionDewey());
        ps.setString(offset + 3, ficha.getEstanteria());
        ps.setString(offset + 4, ficha.getIdioma());
    }

}
