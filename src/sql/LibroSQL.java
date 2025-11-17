
package sql;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 *  Clase de constantes para LibroDao
 *  Tiene como fin definir las sentencias SQL reutilizables
 *  y dejando la capa DAO más limpia
 */

public class LibroSQL {
    
     public static final String INSERT =
        "INSERT INTO libro (eliminado, titulo, autor, editorial, anio_edicion) VALUES (?, ?, ?, ?, ?)";

    public static final String SELECT_BY_ID =
        "SELECT id, eliminado, titulo, autor, editorial, anio_edicion FROM libro WHERE id = ?";

    public static final String SELECT_ALL =
        "SELECT id, eliminado, titulo, autor, editorial, anio_edicion FROM libro WHERE eliminado = 0";

    public static final String UPDATE =
        "UPDATE libro SET eliminado=?, titulo=?, autor=?, editorial=?, anio_edicion=? WHERE id=?";

    public static final String DELETE_LOGICO =
        "UPDATE libro SET eliminado=1 WHERE id=?";

}
