package test;

import service.LibroServiceImpl;
import entities.Libro;
import entities.FichaBibliografica;

public class TestTransaccionSimple {
    public static void main(String[] args) {
        try {
            System.out.println("PRUEBA - TRANSACCIÓN");
            System.out.println("========================================");

            LibroServiceImpl service = new LibroServiceImpl();

            // Libro de prueba
            Libro libro = new Libro();
            libro.setTitulo("Cien años de soledad");
            libro.setAutor("Gabriel García Márquez");
            libro.setEditorial("Sudamericana");
            libro.setAnioEdicion(1967);

            // Ficha de prueba
            FichaBibliografica ficha = new FichaBibliografica();
            ficha.setIsbn("978-8437604947");
            ficha.setClasificacionDewey("863");
            ficha.setEstanteria("LAT-A1");
            ficha.setIdioma("Español");

            System.out.println("Libro: " + libro.getTitulo());
            System.out.println("Ficha: " + ficha.getIsbn());
            System.out.println("Ejecutando transacción...");

            // TRANSACCIÓN REAL
            service.crearLibroConFicha(libro, ficha);

            System.out.println("¡TRANSACCIÓN EXITOSA!");
            System.out.println("Libro creado con ID: " + libro.getId());

            // Verificación adicional
            System.out.println("Verificando en base de datos...");

        } catch (Exception e) {
            System.out.println("Error final: " + e.getMessage());
            e.printStackTrace();
        }
    }
}