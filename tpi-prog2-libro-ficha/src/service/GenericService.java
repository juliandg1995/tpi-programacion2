/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;

/**
 *
 * @author Sandra Martinez
 */
public interface GenericService<T> {

    T crear(T entity) throws Exception;

    T leer(long id) throws Exception;

    void actualizar(T entity) throws Exception;

    void eliminar(long id) throws Exception;

    List<T> leerTodos() throws Exception;
}
