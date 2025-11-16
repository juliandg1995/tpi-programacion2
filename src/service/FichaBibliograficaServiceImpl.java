package service;

import entities.FichaBibliografica;

public class FichaBibliograficaServiceImpl implements FichaBibliograficaService {

    // TODO: Inyectar FichaBibliograficaDAO cuando esté listo
    // private FichaBibliograficaDAO fichaDAO;

    public FichaBibliograficaServiceImpl() {
        // TODO: Inicializar dependencias
    }

    // --- MÉTODOS HEREDADOS DE GenericService ---
    @Override
    public void insertar(FichaBibliografica ficha) throws Exception {
        validarFichaBibliografica(ficha);
        validarIsbnUnico(ficha.getIsbn());
        System.out.println("Insertar ficha bibliográfica - ISBN: " + ficha.getIsbn());
        // TODO: Llamar a fichaDAO.crear(ficha)
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws Exception {
        validarFichaBibliografica(ficha);
        System.out.println("Actualizar ficha bibliográfica - ISBN: " + ficha.getIsbn());
        // TODO: Llamar a fichaDAO.actualizar(ficha)
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // TODO: Lógica de baja lógica
        System.out.println("Eliminar ficha bibliográfica ID: " + id);
    }

    @Override
    public FichaBibliografica buscarPorId(Long id) throws Exception {
        System.out.println("Buscar ficha por ID: " + id);
        return null; // Temporal
    }

    // --- MÉTODOS ESPECÍFICOS DE FichaBibliograficaService ---
    @Override
    public FichaBibliografica buscarPorIsbn(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        System.out.println("Buscar ficha por ISBN: " + isbn);
        return null; // Temporal
    }

    @Override
    public void validarIsbnUnico(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }
        // TODO: Consultar a la base de datos si ya existe este ISBN
        System.out.println("Validando ISBN único: " + isbn);
        // Si existe, lanzar: throw new Exception("El ISBN ya existe en el sistema");
    }

    // --- VALIDACIONES INTERNAS ---
    private void validarFichaBibliografica(FichaBibliografica ficha) {
        if (ficha == null) {
            throw new IllegalArgumentException("La ficha bibliográfica no puede ser nula");
        }
        if (ficha.getIsbn() == null || ficha.getIsbn().trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN es obligatorio");
        }
        if (ficha.getIsbn().length() > 17) {
            throw new IllegalArgumentException("El ISBN no puede exceder 17 caracteres");
        }
        // TODO: Agregar más validaciones según necesidades
    }
}
