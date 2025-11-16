package service;

import java.util.List;

public interface GenericService<T> {
    void insertar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
    T buscarPorId(Long id) throws Exception;  // Este es getById
    List<T> listarTodos() throws Exception;   //
}