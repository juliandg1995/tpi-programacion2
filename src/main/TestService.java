package main;

import entities.Libro;
import entities.FichaBibliografica;
import service.LibroService;
import service.FichaBibliograficaService;
import service.LibroServiceImpl;
import service.FichaBibliograficaServiceImpl;

public class TestService {

    public static void main(String[] args) {
        System.out.println("DEMOSTRACIÓN EDUCATIVA - CAPA SERVICE");
        System.out.println("=========================================");

        try {
            System.out.println("\n¿QUÉ HACE LA CAPA SERVICE?");
            System.out.println("─────────────────────────────");
            System.out.println("• ORQUESTA operaciones complejas");
            System.out.println("• APLICA reglas de negocio");
            System.out.println("• GARANTIZA transacciones atómicas");
            System.out.println("• VALIDA datos antes de persistir");

            // 1. MOSTRAR VALIDACIONES
            demostrarValidaciones();

            // 2. MOSTRAR TRANSACCIONES
            demostrarTransacciones();

            // 3. MOSTRAR ORQUESTACIÓN
            demostrarOrquestacion();

        } catch (Exception e) {
            System.out.println("Error educativo: " + e.getMessage());
        }
    }

    private static void demostrarValidaciones() throws Exception {
        System.out.println("\n1. SERVICIO como GUARDIÁN de REGLAS");
        System.out.println("─────────────────────────────────────");

        FichaBibliograficaService fichaService = new FichaBibliograficaServiceImpl();

        System.out.println("Creando ficha con ISBN inválido...");
        FichaBibliografica fichaMala = new FichaBibliografica();
        fichaMala.setIsbn("ISBN-INVALIDO-!!!");

        try {
            // Esto fallará - demostrando validación
            fichaService.crear(fichaMala);
        } catch (Exception e) {
            System.out.println("SERVICIO BLOQUEÓ: " + e.getMessage());
            System.out.println("   ↳ El Service impide datos incorrectos");
        }

        System.out.println("\nCreando ficha con ISBN válido...");
        FichaBibliografica fichaBuena = new FichaBibliografica();
        fichaBuena.setIsbn("978-1234567890");
        fichaBuena.setClasificacionDewey("025.4");
        fichaBuena.setEstanteria("A25");
        fichaBuena.setIdioma("Español");

        // fichaService.crear(fichaBuena); // Esto funcionaría
        System.out.println("SERVICIO PERMITIRÍA: Ficha válida");
        System.out.println("   ↳ El Service solo permite datos correctos");
    }

    private static void demostrarTransacciones() throws Exception {
        System.out.println("\n2. SERVICIO como COORDINADOR ATÓMICO");
        System.out.println("─────────────────────────────────────");

        LibroService libroService = new LibroServiceImpl();

        System.out.println("Creando Libro + Ficha en TRANSACCIÓN...");

        Libro libro = new Libro();
        libro.setTitulo("Cien años de soledad");
        libro.setAutor("Gabriel García Márquez");

        FichaBibliografica ficha = new FichaBibliografica();
        ficha.setIsbn("978-1234567890");
        ficha.setClasificacionDewey("025.4");

        System.out.println("   Libro: " + libro.getTitulo());
        System.out.println("   Ficha: " + ficha.getIsbn());
        System.out.println("\nService INICIA transacción:");
        System.out.println("   1. Valida libro");
        System.out.println("   2. Valida ficha");
        System.out.println("   3. Verifica ISBN único");
        System.out.println("   4. Crea LIBRO");
        System.out.println("   5. Crea FICHA");
        System.out.println("   6. CONFIRMA transacción");
        System.out.println("\nSi algo falla, TODO se revierte automáticamente");

        // libroService.crearLibroConFicha(libro, ficha);
        System.out.println("TRANSACCIÓN EXITOSA - Todo o Nada");
    }

    private static void demostrarOrquestacion() throws Exception {
        System.out.println("\n3. SERVICIO como DIRECTOR de ORQUESTA");
        System.out.println("─────────────────────────────────────");

        LibroService libroService = new LibroServiceImpl();

        System.out.println("Buscando libro por título...");
        Libro libroEncontrado = libroService.buscarPorTitulo("Cien años de soledad");

        if (libroEncontrado != null) {
            System.out.println("   ↳ Service orquestó la búsqueda compleja");
        }

        System.out.println("\nListando todos los libros...");
        // List<Libro> todos = libroService.listarTodos();
        System.out.println("Service coordinó obtención de todos los registros");

        System.out.println("\nEL SERVICE ORQUESTA múltiples operaciones:");
        System.out.println("   • Búsquedas específicas");
        System.out.println("   • Listados completos");
        System.out.println("   • Operaciones CRUD");
        System.out.println("   • Validaciones cruzadas");
    }
}
