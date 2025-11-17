
package sql;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 *  Clase de constantes para FichaBibliograficaDao
 *  Tiene como fin definir las sentencias SQL reutilizables
 *  y dejando la capa DAO más limpia
 */

public class FichaBibliograficaSQL {
    
    public static final String INSERT =
            "INSERT INTO fichaBibliografica ( id, eliminado, isbn, clasificacionDewey, estanteria, idioma "
            + "VALUES (?,?,?,?,?,?) ";
    
    public static final String SELECT_BY_ID =
            "SELECT id, eliminado, isbn, clasificacionDwey, estanteria, idioma "
            + "FROM fichaBibliografica"
            + "WHERE id = ?";
    
    public static final String SELECT_ALL = 
            "SELECT id, eliminado, isbn, clasificacionDwey, estanteria, idioma "
            + "FROM fichaBibliografica"
            + "WHERE eliminado = 0";
    
    public static final String UPDATE =
            "UPDATE fichaBibliografica SET eliminado=?, isbn=?, clasificacionDwey=?, estanteria=?, idioma=?"
            + "WHERE id = ?";;
    
    public static final String DELETE_LOGICO =
        "UPDATE fichaBibliografica SET eliminado=1 WHERE id=?";

}
