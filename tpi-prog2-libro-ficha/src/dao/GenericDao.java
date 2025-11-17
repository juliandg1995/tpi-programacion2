/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sandra Martinez
 */
public interface GenericDao<T> {
    
    T crear(T entity) throws SQLException;
    T leer(long id) throws SQLException;
    List<T> leerTodos() throws SQLException;
    void actualizar(T entity) throws SQLException;
    void eliminar(long id) throws SQLException; // Baja logica

    T crear(T entity, Connection conn) throws SQLException;
    void actualizar(T entity, Connection conn) throws SQLException;
    void eliminar(long id, Connection conn) throws SQLException;
}