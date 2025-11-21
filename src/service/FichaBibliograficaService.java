package service;

import entities.FichaBibliografica;

public interface FichaBibliograficaService extends GenericService<FichaBibliografica> {
    FichaBibliografica buscarPorIsbn(String isbn) throws Exception;
    void validarIsbnUnico(String isbn) throws Exception;
}
