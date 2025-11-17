/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import config.DatabaseConnection;
import dao.FichaBibliograficaDao;
import dao.LibroDao;
import entities.Libro;
import entities.FichaBibliografica;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Sandra Martinez
 */
public class LibroService implements GenericService<Libro> {

    private final LibroDao libroDao;
    private final FichaBibliograficaDao fichaBibliograficaDao;

    public LibroService(LibroDao libroDao, FichaBibliograficaDao fichaBibliograficaDao) {
        this.libroDao = libroDao;
        this.fichaBibliograficaDao = fichaBibliograficaDao;
    }

    private void validarLibro(Libro libro) throws Exception {
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new Exception("El título del libro es un campo obligatorio.");
        }
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new Exception("El autor del libro es un campo obligatorio.");
        }
    }

    @Override
    public Libro crear(Libro libro) throws Exception {
        validarLibro(libro);

        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 

            if (libro.getFichaBibliografica() != null && libro.getFichaBibliografica().getIsbn() != null) {
                String isbn = libro.getFichaBibliografica().getIsbn();
                if (fichaBibliograficaDao.buscarPorISBN(isbn) != null) {
                    throw new Exception("El ISBN ingresado ya existe en la base de datos.");
                }
            }

            Libro libroInsertado = libroDao.crear(libro, conn);

            if (libro.getFichaBibliografica() != null) {
                FichaBibliografica ficha = libro.getFichaBibliografica();
                long libroId = libroInsertado.getId();

                fichaBibliograficaDao.crearTransaccional(ficha, libroId, conn); 
            }
            
            conn.commit(); 
            return libroInsertado;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("Error transaccional al crear Libro y Ficha: " + e.getMessage(), e);

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    // Ignorar o logear el error
                }
            }
        }
    }
    
    @Override
    public Libro leer(long id) throws Exception {
        Libro libro = libroDao.leer(id);
        if (libro == null) {
            throw new Exception("Libro con ID " + id + " no encontrado.");
        }
        return libro;
    }

    @Override
    public void actualizar(Libro libro) throws Exception {
        validarLibro(libro);
        libroDao.actualizar(libro);
    }

    @Override
    public void eliminar(long id) throws Exception {
        libroDao.eliminar(id);
    }

    @Override
    public List<Libro> leerTodos() throws Exception {
        return libroDao.leerTodos();
    }
}
