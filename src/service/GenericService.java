package service;

public interface GenericService<T> {
    void insertar(T entidad) throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(Long id) throws Exception;
    T buscarPorId(Long id) throws Exception;
}
