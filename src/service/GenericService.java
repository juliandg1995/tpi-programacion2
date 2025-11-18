package service;

import java.util.List;

public interface GenericService<T> {

    void crear(T entidad) throws Exception;
    T buscarPorId(Long id) throws Exception;
    List<T> listarTodos() throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
}