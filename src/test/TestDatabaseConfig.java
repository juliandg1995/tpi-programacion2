package test;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class TestDatabaseConfig {
    public static void main(String[] args) {
        try {
            System.out.println("INICIANDO PRUEBA DE CONFIGURACIÓN BD");
            System.out.println("========================================");

            // 1. Probar que db.properties se carga y conexión funciona
            System.out.println("1. Cargando db.properties...");
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("   db.properties cargado correctamente");
            System.out.println("   Conexión a MySQL exitosa");

            // 2. Verificar información de conexión
            System.out.println("2. Obteniendo información de conexión...");
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("   - URL: " + metaData.getURL());
            System.out.println("   - Usuario: " + metaData.getUserName());
            System.out.println("   - Driver: " + metaData.getDriverName());
            System.out.println("   - Versión: " + metaData.getDatabaseProductVersion());

            // 3. Verificar que podemos ejecutar una consulta simple
            System.out.println("3. Verificando base de datos 'biblioteca_tpi'...");
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery("SELECT DATABASE()");
            if (rs.next()) {
                String dbName = rs.getString(1);
                System.out.println("   - Base de datos actual: " + dbName);
            }

            // 4. Verificar tablas
            System.out.println("4. Verificando tablas...");
            rs = stmt.executeQuery("SHOW TABLES");
            boolean hasTables = false;
            while (rs.next()) {
                System.out.println("   - Tabla: " + rs.getString(1));
                hasTables = true;
            }
            if (!hasTables) {
                System.out.println("   No hay tablas en la base de datos");
            }

            // 5. Cerrar conexión
            conn.close();
            System.out.println("5. Conexión cerrada correctamente");

            System.out.println("CONFIGURACIÓN DE BD 100% FUNCIONAL");
            System.out.println("¡LISTO PARA TRANSACCIONES REALES!");

        } catch (Exception e) {
            System.out.println("ERROR en configuración: " + e.getMessage());
            System.out.println("\nDIAGNÓSTICO Y SOLUCIONES:");

            if (e.getMessage().contains("Unknown database")) {
                System.out.println("   - La base de datos 'biblioteca_tpi' no existe");
                System.out.println("   - SOLUCIÓN: Ejecuta el SQL de creación en MySQL Workbench");
            }
            else if (e.getMessage().contains("Access denied")) {
                System.out.println("   - Error de usuario/password");
                System.out.println("   - SOLUCIÓN: Verifica db.user y db.password en db.properties");
            }
            else if (e.getMessage().contains("Communications link failure")) {
                System.out.println("   - MySQL no está ejecutándose");
                System.out.println("   - SOLUCIÓN: Inicia MySQL en XAMPP");
            }
            else if (e.getMessage().contains("db.properties")) {
                System.out.println("   - No encuentra el archivo de configuración");
                System.out.println("   - SOLUCIÓN: Verifica que db.properties esté en src/");
            }
            else {
                System.out.println("   - Error inesperado: " + e.getClass().getSimpleName());
                e.printStackTrace();
            }
        }
    }
}
