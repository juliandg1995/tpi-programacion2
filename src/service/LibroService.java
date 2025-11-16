package service;

import entities.Libro;
import entities.FichaBibliografica;

public interface LibroService extends GenericService<Libro> {

    // Métodos ESPECÍFICOS de Libro con relación 1→1
    void crearLibroConFicha(Libro libro, FichaBibliografica ficha) throws Exception;
    void asignarFichaBibliografica(Long idLibro, FichaBibliografica ficha) throws Exception;
    Libro buscarPorTitulo(String titulo) throws Exception;
    boolean existeLibroConTitulo(String titulo) throws Exception;
}
