package service;

import entities.FichaBibliografica;

public interface FichaBibliograficaService extends GenericService<FichaBibliografica> {
    FichaBibliografica buscarPorIsbn(String isbn) throws Exception;
}
