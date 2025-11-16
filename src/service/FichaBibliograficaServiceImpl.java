package service;

import entities.FichaBibliografica;
import dao.GenericDao;
import java.sql.Connection;
import java.util.List;

public class FichaBibliograficaServiceImpl implements FichaBibliograficaService {

    // TODO: Inyectar FichaBibliograficaDAO cuando esté listo
    // private FichaBibliograficaDAO fichaDAO;
    // PREPARADO para inyección de DAO
    private GenericDao<FichaBibliografica> fichaDAO;

    public FichaBibliograficaServiceImpl() {
        // TODO: Inicializar dependencias
        // Por ahora vacío - el DAO se inyectará después
    }

    // SETTER para inyección de dependencias
    public void setFichaDAO(GenericDao<FichaBibliografica> fichaDAO) {
        this.fichaDAO = fichaDAO;
    }

    // --- MÉTODOS HEREDADOS DE GenericService ---
    @Override
    public void insertar(FichaBibliografica ficha) throws Exception {
        validarFichaBibliografica(ficha);
        validarIsbnUnico(ficha.getIsbn());
        System.out.println("Insertar ficha bibliográfica - ISBN: " + ficha.getIsbn());
        // TODO: Llamar a fichaDAO.crear(ficha)
        // CUANDO EL DAO ESTÉ LISTO:
        // fichaDAO.crear(ficha);

        System.out.println("Ficha insertada correctamente: " + ficha.getIsbn());
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws Exception {
        validarFichaBibliografica(ficha);
        System.out.println("Actualizar ficha bibliográfica - ISBN: " + ficha.getIsbn());
        // TODO: Llamar a fichaDAO.actualizar(ficha)
        // CUANDO EL DAO ESTÉ LISTO:
        // fichaDAO.actualizar(ficha);

        System.out.println("Ficha actualizada correctamente: " + ficha.getIsbn());
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // TODO: Lógica de baja lógica
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de ficha inválido");
        }

        System.out.println("Eliminando ficha bibliográfica ID: " + id);

        // CUANDO EL DAO ESTÉ LISTO:
        // fichaDAO.eliminar(id);

        System.out.println("Ficha eliminada correctamente ID: " + id);
    }

    @Override
    public FichaBibliografica buscarPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de ficha inválido");
        }

        System.out.println("Buscando ficha por ID: " + id);

        // CUANDO EL DAO ESTÉ LISTO:
        // return fichaDAO.leer(id);

        // Temporal: retornar null simulando que no se encontró
        System.out.println("Búsqueda por ID completada: " + id);
        return null;
    }

    @Override
    public List<FichaBibliografica> listarTodos() throws Exception {
        System.out.println("Listando todas las fichas bibliográficas");

        // CUANDO EL DAO ESTÉ LISTO:
        // return fichaDAO.leerTodos();

        // SIMULACIÓN TEMPORAL
        System.out.println("Listado completado - 0 fichas (simulación)");
        return java.util.Collections.emptyList();
    }

    // --- MÉTODOS ESPECÍFICOS DE FichaBibliograficaService ---
    @Override
    public FichaBibliografica buscarPorIsbn(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }

        System.out.println("Buscando ficha por ISBN: " + isbn);

        // CUANDO EL DAO ESTÉ LISTO, se implementará la búsqueda real
        // Por ahora simulamos la búsqueda

        System.out.println("Búsqueda por ISBN completada: " + isbn);
        return null; // Temporal
    }

    @Override
    public void validarIsbnUnico(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }

        System.out.println("Validando ISBN único: " + isbn);

        // CUANDO EL DAO ESTÉ LISTO:
        // FichaBibliografica existente = buscarPorIsbn(isbn);
        // if (existente != null) {
        //     throw new Exception("El ISBN '" + isbn + "' ya existe en el sistema");
        // }

        System.out.println("ISBN válido y único: " + isbn);
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
        // Validación adicional: formato básico de ISBN
        if (!ficha.getIsbn().matches("[0-9Xx-]+")) {
            throw new IllegalArgumentException("El ISBN contiene caracteres inválidos");
        }
    }
}
