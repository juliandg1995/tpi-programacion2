
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * Clase responsable de obtener una conexión a la base de datos MySQL.
 * Lee las propiedades desde un archivo externo db.properties ubicado en el classpath.
 */

public class DatabaseConnection{

    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() throws SQLException{
        try(InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)){
            if(input == null){
                throw new SQLException("No se encontró el archivo de propiedades " + PROPERTIES_FILE);
            }
            Properties props = new Properties();
            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch(IOException e){
            throw new SQLException("Error al leer el archivo de propiedades de la base de datos", e);
        }
    }
}
