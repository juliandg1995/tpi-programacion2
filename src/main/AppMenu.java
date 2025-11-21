package main;

import service.LibroService;
import service.LibroServiceImpl;
import entities.Libro;
import entities.FichaBibliografica;
import java.util.Scanner;
import java.util.List;
import service.FichaBibliograficaService;
import service.FichaBibliograficaServiceImpl;

/**
 * Menú principal de consola para el Sistema de Gestión de Biblioteca Implementa
 * las operaciones CRUD completas para Libro y FichaBibliografica con manejo
 * robusto de errores y validaciones de entrada
 */
public class AppMenu {

    private LibroService libroService;
    private FichaBibliograficaService fichaService;
    private Scanner scanner;

    public AppMenu() {
        this.libroService = new LibroServiceImpl();
        this.fichaService = new FichaBibliograficaServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Punto de entrada principal del menú Controla el ciclo de vida de la
     * aplicación
     */
    public void iniciar() {
        System.out.println("SISTEMA DE GESTIÓN BIBLIOTECARIA - TFI Programación 2");
        System.out.println("=========================================================");
        mostrarMenuPrincipal();
    }

    /**
     * Menú principal con todas las operaciones CRUD Basado en las
     * especificaciones del trabajo práctico integrador
     */
    private void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Crear Libro con Ficha Bibliográfica");
            System.out.println("2. Buscar Libro por ID");
            System.out.println("3. Listar Todos los Libros");
            System.out.println("4. Actualizar Libro");
            System.out.println("5. Eliminar Libro (Lógico)");
            System.out.println("6. Buscar por ISBN");
            System.out.println("7. Buscar por Título");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    crearLibroConFicha();
                    break;
                case "2":
                    buscarLibroPorId();
                    break;
                case "3":
                    listarTodosLibros();
                    break;
                case "4":
                    actualizarLibro();
                    break;
                case "5":
                    eliminarLibro();
                    break;
                case "6":
                    buscarPorIsbn();
                    break;
                case "7":
                    buscarPorTitulo();
                    break;
                case "0":
                    System.out.println("¡Gracias por usar el Sistema de Gestión Bibliotecaria!");
                    return;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción del 0 al 7.");
            }
        }
    }

    // =========================================================================
    // MÉTODOS PRINCIPALES DE OPERACIONES CRUD
    // =========================================================================
    /**
     * Operación 1: Crear Libro con Ficha Bibliográfica Implementa transacción
     * atómica (todo o nada) Con validación de ISBN único y manejo de errores
     * robusto
     */
    private void crearLibroConFicha() {
        System.out.println("\nCREAR NUEVO LIBRO CON FICHA BIBLIOGRÁFICA");
        System.out.println("--------------------------------------------");

        try {
            // CAPTURA DE DATOS DEL LIBRO
            System.out.println("💽 INGRESE LOS DATOS DEL LIBRO:");

            String titulo = leerCadena("Título: ", true);
            if (titulo == null || titulo.trim().isEmpty()) {
                System.out.println("Operación cancelada: Título es obligatorio");
                return;
            }

            String autor = leerCadena("Autor: ", true);
            if (autor == null || autor.trim().isEmpty()) {
                System.out.println("Operación cancelada: Autor es obligatorio");
                return;
            }

            String editorial = leerCadena("Editorial: ", true);
            Integer anioEdicion = null;

            // Captura opcional del año de edición
            while (true) {
                String anioInput = leerCadena("Año de edición (opcional, Enter para omitir): ", false);
                if (anioInput == null || anioInput.trim().isEmpty()) {
                    break;
                }
                try {
                    anioEdicion = Integer.parseInt(anioInput);
                    if (anioEdicion < 1000 || anioEdicion > java.time.Year.now().getValue()) {
                        System.out.println("Año inválido. Debe estar entre 1000 y " + java.time.Year.now().getValue());
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un año válido (solo números)");
                }
            }

            // CAPTURA DE DATOS DE LA FICHA BIBLIOGRÁFICA
            System.out.println("\nINGRESE LOS DATOS DE LA FICHA BIBLIOGRÁFICA:");

            String isbn = leerCadena("ISBN: ", false);
            if (isbn == null || isbn.trim().isEmpty()) {
                System.out.println("Operación cancelada: ISBN es obligatorio");
                return;
            }

            String clasificacionDewey = leerCadena("Clasificación Dewey: ", true);
            String estanteria = leerCadena("Estantería: ", true);
            String idioma = leerCadena("Idioma: ", true);

            // CREACIÓN DE OBJETOS
            Libro libro = new Libro();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setAnioEdicion(anioEdicion);

            FichaBibliografica ficha = new FichaBibliografica();
            ficha.setIsbn(isbn);
            ficha.setClasificacionDewey(clasificacionDewey);
            ficha.setEstanteria(estanteria);
            ficha.setIdioma(idioma);

            // CONFIRMACIÓN ANTES DE GUARDAR
            System.out.println("\nRESUMEN DE DATOS:");
            System.out.println("   Libro: " + libro.getTitulo() + " - " + libro.getAutor());
            System.out.println("   Editorial: " + (libro.getEditorial() != null ? libro.getEditorial() : "No especificada"));
            System.out.println("   Año: " + (libro.getAnioEdicion() != null ? libro.getAnioEdicion() : "No especificado"));
            System.out.println("   ISBN: " + ficha.getIsbn());
            System.out.println("   Clasificación: " + (ficha.getClasificacionDewey() != null ? ficha.getClasificacionDewey() : "No especificada"));

            String confirmacion = leerCadena("\n¿Confirma la creación? (s/n): ", true);
            if (!"S".equals(confirmacion)) {
                System.out.println("Operación cancelada por el usuario");
                return;
            }

            // EJECUCIÓN DE LA TRANSACCIÓN
            System.out.println("\nGuardando en la base de datos...");
            libroService.crearLibroConFicha(libro, ficha);

            System.out.println("¡Libro creado exitosamente!");
            System.out.println("ID asignado: " + libro.getId());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("ISBN: " + ficha.getIsbn());

        } catch (Exception e) {
            manejarError(e, "crear libro con ficha");
        } finally {
            pausar("");
        }
    }

    /**
     * Operación 2: Buscar Libro por ID Con manejo de ID inexistente
     */
    private void buscarLibroPorId() {
        System.out.println("\nBUSCAR LIBRO POR ID");
        System.out.println("----------------------");

        try {
            Long id = leerId("Ingrese el ID del libro: ");
            if (id == null) {
                System.out.println("Operación cancelada: No se ingresó un ID.");
                return;
            }

            // Buscar libro usando la capa service
            Libro libro = libroService.buscarPorId(id);

            // Mostrar resultado
            System.out.println("\nLIBRO ENCONTRADO:");
            System.out.println("------------------");
            System.out.println("ID: " + libro.getId());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Editorial: " + (libro.getEditorial() != null ? libro.getEditorial() : "No especificada"));
            System.out.println("Año Edición: " + (libro.getAnioEdicion() != null ? libro.getAnioEdicion() : "No especificado"));
            System.out.println("Eliminado: " + (libro.getEliminado() ? "Sí" : "No"));

            // Mostrar ficha bibliográfica asociada
            FichaBibliografica ficha = libro.getFichaBibliografica();
            if (ficha != null) {
                System.out.println("\nFICHA BIBLIOGRÁFICA:");
                System.out.println("---------------------");
                System.out.println("ISBN: " + ficha.getIsbn());
                System.out.println("Clasificación Dewey: "
                        + (ficha.getClasificacionDewey() != null ? ficha.getClasificacionDewey() : "No especificada"));
                System.out.println("Estantería: "
                        + (ficha.getEstanteria() != null ? ficha.getEstanteria() : "No especificada"));
                System.out.println("Idioma: "
                        + (ficha.getIdioma() != null ? ficha.getIdioma() : "No especificado"));
            } else {
                System.out.println("\nNo existe ficha bibliográfica asociada.");
            }

        } catch (Exception e) {
            manejarError(e, "buscar libro por ID");
        } finally {
            pausar("");
        }
    }

    /**
     * Clase helper: Recorta una cadena a un largo máximo para que la tabla no
     * se desarme.
     */
    private String recortar(String texto, int max) {
        if (texto == null) {
            return "-";
        }
        return texto.length() > max ? texto.substring(0, max - 3) + "..." : texto;
    }

    /**
     * Operación 3: Listar Todos los Libros Muestra formato tabla
     */
    private void listarTodosLibros() {
        System.out.println("\nLISTADO COMPLETO DE LIBROS");
        System.out.println("----------------------------");

        try {
            // Llamamos a la capa service
            List<Libro> libros = libroService.listarTodos();

            if (libros == null || libros.isEmpty()) {
                System.out.println("No hay libros cargados en el sistema.");
            } else {
                // Encabezado de tabla
                System.out.printf("%-5s %-30s %-25s %-20s %-6s %-17s%n",
                        "ID", "TÍTULO", "AUTOR", "EDITORIAL", "AÑO", "ISBN");

                System.out.println("------------------------------------------------------------------------------------------");

                for (Libro libro : libros) {
                    FichaBibliografica ficha = libro.getFichaBibliografica();
                    String isbn = (ficha != null && ficha.getIsbn() != null)
                            ? ficha.getIsbn()
                            : "-";

                    String editorial = libro.getEditorial() != null ? libro.getEditorial() : "-";
                    String anio = libro.getAnioEdicion() != null ? libro.getAnioEdicion().toString() : "-";

                    System.out.printf("%-5d %-30s %-25s %-20s %-6s %-17s%n",
                            libro.getId(),
                            recortar(libro.getTitulo(), 30),
                            recortar(libro.getAutor(), 25),
                            recortar(editorial, 20),
                            anio,
                            isbn);
                }
            }

        } catch (Exception e) {
            manejarError(e, "listar todos los libros");
        } finally {
            pausar("");
        }
    }

    /**
     * Operación 4: Actualizar Libro existente Permite actualización parcial
     * (mantener valores actuales)
     */
    private void actualizarLibro() {
        System.out.println("\nACTUALIZAR LIBRO");
        System.out.println("------------------");

        try {
            Long id = leerId("Ingrese el ID del libro a actualizar (o Enter para cancelar): ");
            if (id == null) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            // Buscar el libro actual
            Libro libro = libroService.buscarPorId(id);

            System.out.println("\nDATOS ACTUALES DEL LIBRO:");
            System.out.println("ID       : " + libro.getId());
            System.out.println("Título   : " + libro.getTitulo());
            System.out.println("Autor    : " + libro.getAutor());
            System.out.println("Editorial: " + (libro.getEditorial() != null ? libro.getEditorial() : "-"));
            System.out.println("Año      : " + (libro.getAnioEdicion() != null ? libro.getAnioEdicion() : "-"));

            System.out.println("\nIngrese los nuevos datos (Enter para mantener el valor actual):");

            // Título
            String nuevoTitulo = leerCadena("Nuevo título [" + libro.getTitulo() + "]: ", true);
            if (nuevoTitulo != null && !nuevoTitulo.trim().isEmpty()) {
                libro.setTitulo(nuevoTitulo);
            }

            // Autor
            String nuevoAutor = leerCadena("Nuevo autor [" + libro.getAutor() + "]: ", true);
            if (nuevoAutor != null && !nuevoAutor.trim().isEmpty()) {
                libro.setAutor(nuevoAutor);
            }

            // Editorial
            String nuevaEditorial = leerCadena(
                    "Nueva editorial [" + (libro.getEditorial() != null ? libro.getEditorial() : "-") + "]: ",
                    true);
            if (nuevaEditorial != null && !nuevaEditorial.trim().isEmpty()) {
                libro.setEditorial(nuevaEditorial);
            }

            // Año de edición
            while (true) {
                String actualAnio = (libro.getAnioEdicion() != null ? libro.getAnioEdicion().toString() : "sin año");
                String nuevoAnioStr = leerCadena(
                        "Nuevo año de edición [" + actualAnio + "] (Enter para mantener): ",
                        false);

                if (nuevoAnioStr == null || nuevoAnioStr.trim().isEmpty()) {
                    break; // mantener el actual
                }

                try {
                    int nuevoAnio = Integer.parseInt(nuevoAnioStr);
                    int anioActual = java.time.Year.now().getValue();

                    if (nuevoAnio < 1000 || nuevoAnio > anioActual) {
                        System.out.println("Año inválido. Debe estar entre 1000 y " + anioActual);
                        continue;
                    }
                    libro.setAnioEdicion(nuevoAnio);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un año válido (solo números).");
                }
            }

            // Confirmación
            String confirmacion = leerCadena("\n¿Confirma la actualización? (s/n): ", true);
            if (!"S".equals(confirmacion)) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            // Persistir cambios
            libroService.actualizar(libro);
            System.out.println("Libro actualizado correctamente.");

        } catch (Exception e) {
            manejarError(e, "actualizar libro");
        } finally {
            pausar("");
        }
    }

    /**
     * Operación 5: Eliminación Lógica de Libro No elimina físicamente, marca
     * como eliminado
     */
    private void eliminarLibro() {
        System.out.println("\nELIMINAR LIBRO (LÓGICO)");
        System.out.println("-------------------------");

        try {
            Long id = leerId("Ingrese el ID del libro a eliminar (o Enter para cancelar): ");
            if (id == null) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            // Mostrar datos del libro antes de eliminar
            Libro libro = libroService.buscarPorId(id);

            System.out.println("\nLibro seleccionado:");
            System.out.println("ID     : " + libro.getId());
            System.out.println("Título : " + libro.getTitulo());
            System.out.println("Autor  : " + libro.getAutor());

            String confirmacion = leerCadena("¿Confirma la eliminación lógica? (s/n): ", true);
            if (!"S".equals(confirmacion)) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            libroService.eliminar(id);
            System.out.println("Libro marcado como eliminado correctamente.");

        } catch (Exception e) {
            manejarError(e, "eliminar libro");
        } finally {
            pausar("");
        }
    }

    /**
     * Operación 6: Búsqueda por ISBN (campo relevante) Búsqueda exacta por ISBN
     * único
     */
    private void buscarPorIsbn() {
        System.out.println("\nBUSCAR POR ISBN");
        System.out.println("------------------");

        try {
            String isbn = leerCadena("Ingrese el ISBN (o Enter para cancelar): ", false);
            if (isbn == null || isbn.trim().isEmpty()) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            // Buscar ficha por ISBN
            FichaBibliografica ficha = fichaService.buscarPorIsbn(isbn);
            if (ficha == null) {
                System.out.println("No se encontró ninguna ficha con el ISBN: " + isbn);
                return;
            }

            // Obtener el libro asociado (PK compartida)
            Libro libro = libroService.buscarPorId(ficha.getId());

            System.out.println("\nRESULTADO ENCONTRADO:");
            System.out.println("ID Libro : " + libro.getId());
            System.out.println("Título   : " + libro.getTitulo());
            System.out.println("Autor    : " + libro.getAutor());
            System.out.println("Editorial: " + (libro.getEditorial() != null ? libro.getEditorial() : "-"));
            System.out.println("Año      : " + (libro.getAnioEdicion() != null ? libro.getAnioEdicion() : "-"));
            System.out.println("ISBN     : " + ficha.getIsbn());
            System.out.println("Clasif.  : " + (ficha.getClasificacionDewey() != null ? ficha.getClasificacionDewey() : "-"));
            System.out.println("Estant.  : " + (ficha.getEstanteria() != null ? ficha.getEstanteria() : "-"));
            System.out.println("Idioma   : " + (ficha.getIdioma() != null ? ficha.getIdioma() : "-"));

        } catch (Exception e) {
            manejarError(e, "buscar por ISBN");
        } finally {
            pausar("");
        }
    }

    /**
     * Operación 7: Búsqueda por Título Búsqueda flexible con coincidencias
     * parciales
     */
    private void buscarPorTitulo() {
        System.out.println("\nBUSCAR POR TÍTULO");
        System.out.println("-------------------");

        try {
            String titulo = leerCadena("Ingrese el título (o Enter para cancelar): ", true);
            if (titulo == null || titulo.trim().isEmpty()) {
                System.out.println("Operación cancelada por el usuario.");
                return;
            }

            Libro libro = libroService.buscarPorTitulo(titulo);

            if (libro == null) {
                System.out.println("No se encontró ningún libro con ese título.");
                return;
            }

            System.out.println("\nRESULTADO ENCONTRADO:");
            System.out.println("ID       : " + libro.getId());
            System.out.println("Título   : " + libro.getTitulo());
            System.out.println("Autor    : " + libro.getAutor());
            System.out.println("Editorial: " + (libro.getEditorial() != null ? libro.getEditorial() : "-"));
            System.out.println("Año      : " + (libro.getAnioEdicion() != null ? libro.getAnioEdicion() : "-"));

        } catch (Exception e) {
            manejarError(e, "buscar por título");
        } finally {
            pausar("");
        }
    }

    // =========================================================================
    // MÉTODOS AUXILIARES PARA MANEJO DE ENTRADAS
    // =========================================================================
    /**
     * Lee una cadena de texto con opción de conversión a mayúsculas
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @param convertirMayusculas true para convertir a mayúsculas
     * @return Cadena ingresada por el usuario
     */
    private String leerCadena(String mensaje, boolean convertirMayusculas) {
        System.out.print(mensaje);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        return convertirMayusculas ? input.toUpperCase() : input;
    }

    /**
     * Lee y valida un ID numérico
     *
     * @param mensaje Mensaje a mostrar al usuario
     * @return ID válido o null si se ingresa vacío
     */
    private Long leerId(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null;
                }
                long id = Long.parseLong(input);
                if (id <= 0) {
                    throw new NumberFormatException("ID debe ser positivo");
                }
                return id;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un ID válido (número positivo)");
            }
        }
    }

    /**
     * Manejo centralizado de errores con mensajes específicos
     *
     * @param e Excepción ocurrida
     * @param operacion Nombre de la operación que falló
     */
    private void manejarError(Exception e, String operacion) {
        String mensaje = e.getMessage();

        if (mensaje.contains("Duplicate entry") && mensaje.contains("isbn")) {
            System.out.println("Error: El ISBN ya existe en el sistema");
        } else if (mensaje.contains("cannot be null")) {
            System.out.println("Error: Campos obligatorios faltantes");
        } else if (mensaje.contains("doesn't exist") || mensaje.contains("No se encontró")) {
            System.out.println("Error: Registro no encontrado");
        } else if (mensaje.contains("Data truncation")) {
            System.out.println("Error: Datos demasiado largos para el campo");
        } else if (mensaje.contains("Communications link failure")) {
            System.out.println("Error: No se puede conectar a la base de datos");
        } else {
            System.out.println("Error en " + operacion + ": " + mensaje);
        }
    }

    /**
     * Pausa la ejecución hasta que el usuario presione Enter
     *
     * @param mensaje Mensaje a mostrar antes de la pausa
     */
    private void pausar(String mensaje) {
        System.out.print(mensaje + " Presione Enter para continuar...");
        scanner.nextLine();
    }
}
