
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

public class FichaBibliograficaDao implements GenericDao<FichaBibliografica>{

    @Override
    public void crear(FichaBibliografica ficha) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            crear(ficha, conn);
        }
    }

    public void crear(FichaBibliografica ficha, Connection conn) throws SQLException{
        String sql = FichaBibliograficaSQL.INSERT;
        
        // Se utiliza prepareStatement como medida de seguridad para evitar inyección de SQL por medio de los parámetros
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, ficha.getId());
            ps.setBoolean(2, ficha.getEliminado() != null ? ficha.getEliminado() : false);
            ps.setString(3, ficha.getIsbn());
            ps.setString(4, ficha.getClasificacionDewey());
            ps.setString(5, ficha.getEstanteria());
            ps.setString(6, ficha.getIdioma());
            ps.executeUpdate();
        }
    }

    @Override
    public FichaBibliografica leer(long id) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            return leer(id, conn);
        }
    }

    public FichaBibliografica leer(long id, Connection conn) throws SQLException{
        String sql = FichaBibliograficaSQL.SELECT_BY_ID;
        
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    FichaBibliografica ficha = new FichaBibliografica();
                    ficha.setId(rs.getLong("id"));
                    ficha.setEliminado(rs.getBoolean("eliminado"));
                    ficha.setIsbn(rs.getString("isbn"));
                    ficha.setClasificacionDewey(rs.getString("clasificacion_dewey"));
                    ficha.setEstanteria(rs.getString("estanteria"));
                    ficha.setIdioma(rs.getString("idioma"));
                    return ficha;
                }
            }
        }
        return null;
    }

    @Override
    public List<FichaBibliografica> leerTodos() throws SQLException{
        List<FichaBibliografica> lista = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getConnection()){
            String sql = FichaBibliograficaSQL.SELECT_ALL;
            try(PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    FichaBibliografica ficha = new FichaBibliografica();
                    ficha.setId(rs.getLong("id"));
                    ficha.setEliminado(rs.getBoolean("eliminado"));
                    ficha.setIsbn(rs.getString("isbn"));
                    ficha.setClasificacionDewey(rs.getString("clasificacion_dewey"));
                    ficha.setEstanteria(rs.getString("estanteria"));
                    ficha.setIdioma(rs.getString("idioma"));
                    lista.add(ficha);
                }
            }
        }
        return lista;
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            actualizar(ficha, conn);
        }
    }

    public void actualizar(FichaBibliografica ficha, Connection conn) throws SQLException{
        String sql = FichaBibliograficaSQL.UPDATE;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBoolean(1, ficha.getEliminado() != null ? ficha.getEliminado() : false);
            ps.setString(2, ficha.getIsbn());
            ps.setString(3, ficha.getClasificacionDewey());
            ps.setString(4, ficha.getEstanteria());
            ps.setString(5, ficha.getIdioma());
            ps.setLong(6, ficha.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws SQLException{
        try(Connection conn = DatabaseConnection.getConnection()){
            eliminar(id, conn);
        }
    }

    public void eliminar(long id, Connection conn) throws SQLException{
        String sql = "UPDATE ficha_bibliografica SET eliminado = 1 WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }
}
