/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Sandra Martinez
 */
public class DatabaseConnection {
    
    // URL completa usando el host y la base de datos que creaste
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/tpi_libros_prueba?serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASS = "@Lala5610"; 
    
    private DatabaseConnection() {}
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
