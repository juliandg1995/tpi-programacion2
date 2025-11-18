package test;

import service.validations.ValidacionService;
import entities.Libro;
import entities.FichaBibliografica;

public class TestSinDependenciasCirculares {
    public static void main(String[] args) {
        try {
            System.out.println("PROBANDO SIN DEPENDENCIAS CIRCULARES");
            System.out.println("========================================");

            // 1. Probar validaciones básicas
            Libro libro = new Libro();
            libro.setTitulo("Libro Prueba");
            libro.setAutor("Autor Prueba");

            FichaBibliografica ficha = new FichaBibliografica();
            ficha.setIsbn("978-9999999999"); // ISBN único

            ValidacionService.validarLibro(libro);
            System.out.println("Validación de libro OK");

            ValidacionService.validarFichaBibliografica(ficha);
            System.out.println("Validación de ficha OK");

            // 2. Probar validación de ISBN único (sin dependencia circular)
            ValidacionService.validarIsbnUnico("978-9999999999");
            System.out.println("Validación ISBN único OK");

            System.out.println("SISTEMA SIN DEPENDENCIAS CIRCULARES - LISTO");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
