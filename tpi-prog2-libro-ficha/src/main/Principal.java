/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import dao.FichaBibliograficaDao;
import dao.LibroDao;
import entities.FichaBibliografica;
import entities.Libro;
import service.LibroService;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Sandra Martinez
 */
public class Principal {

    private final LibroService libroService;
    private final Scanner scanner;

    public Principal() {
        LibroDao libroDao = new LibroDao();
        FichaBibliograficaDao fichaBibliograficaDao = new FichaBibliograficaDao();
        
        this.libroService = new LibroService(libroDao, fichaBibliograficaDao);
        this.scanner = new Scanner(System.in);
    }

    public void ejecutar() {
        int opcion = -1;
        do {
            mostrarMenu();
            try {
                String input = scanner.nextLine().trim();
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1: crearLibro(); break;
                    case 2: listarLibros(); break;
                    case 3: buscarLibroPorId(); break;
                    case 4: actualizarLibro(); break;
                    case 5: eliminarLibroLogico(); break;
                    case 0: break;
                    default: System.out.println("Opción no válida.");
                }

            } catch (NumberFormatException e) {
                System.err.println("ERROR: Ingrese un número válido.");
            } catch (Exception e) {
                System.err.println("ERROR DE LA APLICACIÓN: " + e.getMessage());
            }

        } while (opcion != 0);

        System.out.println("Aplicación finalizada.");
        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("\n========== MENÚ LIBROS ==========");
        System.out.println("1. Crear Nuevo Libro (Transaccional)");
        System.out.println("2. Listar Todos los Libros");
        System.out.println("3. Buscar Libro por ID");
        System.out.println("4. Actualizar Libro");
        System.out.println("5. Eliminar Libro (Baja Lógica)");
        System.out.println("0. Salir");
        System.out.print("Ingrese opción: ");
    }
    
    private void crearLibro() throws Exception {
        System.out.println("\n--- Creación de Libro ---");
        System.out.print("Título (Obligatorio): ");
        String titulo = scanner.nextLine().trim();

        System.out.print("Autor (Obligatorio): ");
        String autor = scanner.nextLine().trim();
        
        System.out.print("Editorial: ");
        String editorial = scanner.nextLine().trim();

        System.out.print("Año de Edición (Enter para omitir): ");
        String anioInput = scanner.nextLine().trim();
        Integer anio = anioInput.isEmpty() ? null : Integer.parseInt(anioInput);
        
        Libro nuevoLibro = new Libro(null, false, titulo, autor, editorial, anio, null);

        System.out.print("Desea agregar Ficha Bibliográfica? (s/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("s")) {
            System.out.print("ISBN (Obligatorio y Único): ");
            String isbn = scanner.nextLine().trim();
            
            System.out.print("Idioma: ");
            String idioma = scanner.nextLine().trim();
            
            FichaBibliografica nuevaFicha = new FichaBibliografica(null, false, isbn, null, null, idioma);
            nuevoLibro.setFichaBibliografica(nuevaFicha);
        }
        
        Libro libroCreado = libroService.crear(nuevoLibro);
        System.out.println("¡Éxito! Libro y Ficha creados. ID asignado: " + libroCreado.getId());
    }

    private void listarLibros() throws Exception {
        System.out.println("\n--- Listado de Libros Activos ---");
        List<Libro> libros = libroService.leerTodos();
        
        if (libros.isEmpty()) {
            System.out.println("No hay libros activos para mostrar.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println(libro.toString());
        }
    }
    
    private void buscarLibroPorId() throws Exception {
        System.out.print("Ingrese el ID del libro a buscar: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        
        Libro libro = libroService.leer(id);
        
        System.out.println("Libro encontrado:");
        System.out.println(libro.toString());
    }

    private void actualizarLibro() throws Exception {
        System.out.print("Ingrese el ID del libro a actualizar: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        
        Libro libroActual = libroService.leer(id);
        
        System.out.println("--- Actualizando Libro ID: " + id + " ---");
        System.out.println("Título actual: " + libroActual.getTitulo());
        System.out.print("Nuevo Título (Enter para mantener): ");
        String nuevoTitulo = scanner.nextLine().trim();
        if (!nuevoTitulo.isEmpty()) {
            libroActual.setTitulo(nuevoTitulo);
        }
        
        libroService.actualizar(libroActual);
        System.out.println("¡Éxito! Libro ID " + id + " actualizado correctamente.");
    }

    private void eliminarLibroLogico() throws Exception {
        System.out.print("Ingrese el ID del libro a eliminar lógicamente: ");
        long id = Long.parseLong(scanner.nextLine().trim());
        
        libroService.eliminar(id);
        System.out.println("¡Éxito! Libro ID " + id + " marcado como eliminado.");
    }


    public static void main(String[] args) {
        Principal menu = new Principal();
        menu.ejecutar();
    }
}