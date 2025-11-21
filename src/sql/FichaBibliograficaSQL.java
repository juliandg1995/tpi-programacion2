package sql;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * Clase de constantes para FichaBibliograficaDao
 * Tiene como fin definir las sentencias SQL reutilizables
 * y dejando la capa DAO más limpia
 */

public class FichaBibliograficaSQL {

    public static final String INSERT =
            "INSERT INTO ficha_bibliografica (id, eliminado, isbn, clasificacion_dewey, estanteria, idioma) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_BY_ID =
            "SELECT id, eliminado, isbn, clasificacion_dewey, estanteria, idioma " +
                    "FROM ficha_bibliografica " +
                    "WHERE id = ? AND eliminado = false";

    public static final String SELECT_ALL =
            "SELECT id, eliminado, isbn, clasificacion_dewey, estanteria, idioma " +
                    "FROM ficha_bibliografica " +
                    "WHERE eliminado = false";

    public static final String UPDATE =
            "UPDATE ficha_bibliografica SET eliminado = ?, isbn = ?, clasificacion_dewey = ?, estanteria = ?, idioma = ? " +
                    "WHERE id = ?";

    public static final String DELETE_LOGICO =
            "UPDATE ficha_bibliografica SET eliminado = true WHERE id = ?";

}
