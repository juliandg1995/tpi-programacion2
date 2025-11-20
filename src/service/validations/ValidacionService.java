package service.validations;

import entities.FichaBibliografica;
import entities.Libro;
import java.util.List;

/**
 * Servicio centralizado para validaciones de negocio
 * Implementa todas las validaciones requeridas para el dominio Libro → FichaBibliografica
 */
public class ValidacionService {

    // =========================================================================
    // VALIDACIONES DE FICHA BIBLIOGRÁFICA
    // =========================================================================

    /**
     * Valida todos los aspectos de una FichaBibliografica
     * @param ficha La ficha a validar
     * @throws IllegalArgumentException si la validación falla
     */
    public static void validarFichaBibliografica(FichaBibliografica ficha) {
        if (ficha == null) {
            throw new IllegalArgumentException("La ficha bibliográfica no puede ser nula");
        }

        // Validación de ISBN
        if (ficha.getIsbn() == null || ficha.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es obligatorio");
        }

        String isbn = ficha.getIsbn().trim();
        if (isbn.length() > 17) {
            throw new IllegalArgumentException("El ISBN no puede exceder 17 caracteres");
        }

        if (!isbn.matches("[0-9Xx-]+")) {
            throw new IllegalArgumentException("El ISBN solo puede contener números, 'X', 'x' y guiones");
        }

        // Validación de clasificación Dewey (si está presente)
        if (ficha.getClasificacionDewey() != null && ficha.getClasificacionDewey().length() > 20) {
            throw new IllegalArgumentException("La clasificación Dewey no puede exceder 20 caracteres");
        }

        // Validación de estantería (si está presente)
        if (ficha.getEstanteria() != null && ficha.getEstanteria().length() > 20) {
            throw new IllegalArgumentException("La estantería no puede exceder 20 caracteres");
        }

        // Validación de idioma (si está presente)
        if (ficha.getIdioma() != null && ficha.getIdioma().length() > 30) {
            throw new IllegalArgumentException("El idioma no puede exceder 30 caracteres");
        }

        System.out.println("Ficha bibliográfica validada correctamente - ISBN: " + isbn);
    }

    // =========================================================================
    // VALIDACIONES DE LIBRO
    // =========================================================================

    /**
     * Valida todos los aspectos de un Libro
     * @param libro El libro a validar
     * @throws IllegalArgumentException si la validación falla
     */
    public static void validarLibro(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }

        // Validación de título
        if (libro.getTitulo() == null || libro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El título del libro es obligatorio");
        }

        String titulo = libro.getTitulo().trim();
        if (titulo.length() > 150) {
            throw new IllegalArgumentException("El título no puede exceder 150 caracteres");
        }

        // Validación de autor
        if (libro.getAutor() == null || libro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("El autor del libro es obligatorio");
        }

        String autor = libro.getAutor().trim();
        if (autor.length() > 120) {
            throw new IllegalArgumentException("El autor no puede exceder 120 caracteres");
        }

        // Validación de editorial (si está presente)
        if (libro.getEditorial() != null && libro.getEditorial().length() > 100) {
            throw new IllegalArgumentException("La editorial no puede exceder 100 caracteres");
        }

        // Validación de año de edición (si está presente)
        if (libro.getAnioEdicion() != null) {
            int anio = libro.getAnioEdicion();
            int anioActual = java.time.Year.now().getValue();

            if (anio < 1000 || anio > anioActual + 1) { // +1 para libros por publicar
                throw new IllegalArgumentException("El año de edición debe ser entre 1000 y " + (anioActual + 1));
            }
        }

        System.out.println("Libro validado correctamente - Título: " + titulo);
    }

    // =========================================================================
    // VALIDACIONES DE UNICIDAD Y REGLAS DE NEGOCIO
    // =========================================================================

    /**
     * Valida que un ISBN sea único en el sistema
     * @param isbn El ISBN a validar
     * @throws Exception si el ISBN ya existe
     */
    public static void validarIsbnUnico(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío para validar unicidad");
        }

        System.out.println("Validando unicidad de ISBN: " + isbn);

        // COMENTAR TEMPORALMENTE LA VALIDACIÓN INEFICIENTE
        /*
        try {
            dao.FichaBibliograficaDao fichaDao = new dao.FichaBibliograficaDao();

            // Buscar si existe alguna ficha con este ISBN
            List<FichaBibliografica> todasLasFichas = fichaDao.leerTodos();
            for (FichaBibliografica ficha : todasLasFichas) {
                if (isbn.equals(ficha.getIsbn())) {
                    throw new Exception("El ISBN '" + isbn + "' ya existe en el sistema");
                }
            }

            System.out.println("ISBN único validado: " + isbn);

        } catch (Exception e) {
            // Si es error de unicidad, relanzar, sino error genérico
            if (e.getMessage().contains("ya existe")) {
                throw e;
            }
            throw new Exception("Error al validar ISBN único: " + e.getMessage());
        }
        */
    }

    /**
     * Valida que un título de libro sea único en el sistema
     * @param titulo El título a validar
     * @throws Exception si el título ya existe
     */
    public static void validarTituloUnico(String titulo) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        System.out.println("Validando unicidad de título: " + titulo);

        // SIMULACIÓN TEMPORAL - Cuando el DAO esté listo, aquí irá la consulta real
        // Libro existente = libroDao.buscarPorTitulo(titulo);
        // if (existente != null) {
        //     throw new Exception("El título '" + titulo + "' ya existe en el sistema");
        // }

        // Simulación de títulos existentes (para pruebas)
        if (titulo.equalsIgnoreCase("Cien años de soledad") ||
                titulo.equalsIgnoreCase("El Quijote")) {
            throw new Exception("El título '" + titulo + "' ya existe en el sistema (simulación)");
        }

        System.out.println("Título único validado: " + titulo);
    }

    // =========================================================================
    // VALIDACIONES DE ID Y REFERENCIAS
    // =========================================================================

    /**
     * Valida que un ID sea válido (no nulo y positivo)
     * @param id El ID a validar
     * @param entidad Nombre de la entidad para el mensaje de error
     * @throws IllegalArgumentException si el ID es inválido
     */
    public static void validarId(Long id, String entidad) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de " + entidad + " no puede ser nulo");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de " + entidad + " debe ser un número positivo");
        }
    }
}
