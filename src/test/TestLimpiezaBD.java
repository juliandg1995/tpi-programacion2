package test;

import java.sql.Connection;
import java.sql.Statement;
import config.DatabaseConnection;

public class TestLimpiezaBD {
    public static void main(String[] args) {
        try {
            System.out.println("LIMPIANDO DATOS DE PRUEBA");
            System.out.println("=============================");

            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();

            // Eliminar todos los registros de prueba
            stmt.executeUpdate("DELETE FROM ficha_bibliografica");
            stmt.executeUpdate("DELETE FROM libro");

            // Reiniciar auto_increment
            stmt.executeUpdate("ALTER TABLE libro AUTO_INCREMENT = 1");
            stmt.executeUpdate("ALTER TABLE ficha_bibliografica AUTO_INCREMENT = 1");

            conn.close();
            System.out.println("Todos los datos de prueba eliminados");
            System.out.println("Auto_increment reiniciado a 1");
            System.out.println("Ahora puedes usar el AppMenu sin problemas de ISBN duplicado");

        } catch (Exception e) {
            System.out.println("Error en limpieza: " + e.getMessage());
        }
    }
}
