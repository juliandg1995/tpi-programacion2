
package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * @param <T>
 */

public interface GenericDao<T> {
    
     void crear(T entidad) throws SQLException;

    T leer(long id) throws SQLException;

    List<T> leerTodos() throws SQLException;

    void actualizar(T entidad) throws SQLException;

    void eliminar(long id) throws SQLException;
    
}
