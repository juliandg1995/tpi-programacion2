package service;

import entities.FichaBibliografica;
import dao.FichaBibliograficaDao;
import java.sql.Connection;
import java.util.List;
import service.validations.ValidacionService;

public class FichaBibliograficaServiceImpl implements FichaBibliograficaService {

    private FichaBibliograficaDao fichaDAO;

    public FichaBibliograficaServiceImpl() {
        this.fichaDAO = new FichaBibliograficaDao();
    }

    // SETTER para inyección de dependencias (opcional)
    public void setFichaDAO(FichaBibliograficaDao fichaDAO) {
        this.fichaDAO = fichaDAO;
    }

    // --- MÉTODOS HEREDADOS DE GenericService ---

    @Override
    public FichaBibliografica crear(FichaBibliografica ficha) throws Exception {
        ValidacionService.validarFichaBibliografica(ficha);
        validarIsbnUnico(ficha.getIsbn());

        System.out.println("Creando ficha bibliográfica - ISBN: " + ficha.getIsbn());

        fichaDAO.crear(ficha);

        System.out.println("Ficha creada correctamente: " + ficha.getIsbn());
        return ficha;
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws Exception {
        ValidacionService.validarFichaBibliografica(ficha);

        // Verificar que la ficha existe antes de actualizar
        FichaBibliografica existente = buscarPorId(ficha.getId());
        if (existente == null) {
            throw new Exception("No se puede actualizar: no existe ficha con ID " + ficha.getId());
        }

        System.out.println("Actualizando ficha bibliográfica - ISBN: " + ficha.getIsbn());

        fichaDAO.actualizar(ficha);

        System.out.println("Ficha actualizada correctamente: " + ficha.getIsbn());
    }

    @Override
    public void eliminar(Long id) throws Exception {
        ValidacionService.validarId(id, "ficha bibliográfica");

        // Verificar que existe antes de eliminar
        FichaBibliografica existente = buscarPorId(id);
        if (existente == null) {
            throw new Exception("No se puede eliminar: no existe ficha con ID " + id);
        }

        System.out.println("Eliminando ficha bibliográfica ID: " + id);

        // LLAMADA AL DAO (eliminación lógica)
        fichaDAO.eliminar(id);

        System.out.println("Ficha eliminada correctamente ID: " + id);
    }

    @Override
    public FichaBibliografica buscarPorId(Long id) throws Exception {
        ValidacionService.validarId(id, "ficha bibliográfica");
        System.out.println("Buscando ficha por ID: " + id);

        // LLAMADA REAL AL DAO DE JULIÁN
        FichaBibliografica ficha = fichaDAO.leer(id);

        if (ficha == null) {
            throw new Exception("No se encontró ficha con ID: " + id);
        }

        System.out.println("Ficha encontrada - ID: " + id + ", ISBN: " + ficha.getIsbn());
        return ficha;
    }

    @Override
    public List<FichaBibliografica> listarTodos() throws Exception {
        System.out.println("Listando todas las fichas bibliográficas");

        List<FichaBibliografica> fichas = fichaDAO.leerTodos();

        System.out.println("Listado completado - " + fichas.size() + " fichas encontradas");
        return fichas;
    }

    // --- MÉTODOS ESPECÍFICOS DE FichaBibliograficaService ---

    @Override
    public FichaBibliografica buscarPorIsbn(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }

        // Validar formato básico de ISBN
        if (!isbn.matches("[0-9Xx-]+")) {
            throw new IllegalArgumentException("Formato de ISBN inválido. Solo números, X y guiones permitidos");
        }

        System.out.println("Buscando ficha por ISBN: " + isbn);

        // LLAMADA AL DAO - necesitamos implementar este método en el DAO
        // Por ahora usamos una implementación temporal
        List<FichaBibliografica> todasLasFichas = listarTodos();
        for (FichaBibliografica ficha : todasLasFichas) {
            if (isbn.equals(ficha.getIsbn())) {
                System.out.println("Ficha encontrada: " + isbn);
                return ficha;
            }
        }

        System.out.println("Ficha NO encontrada: " + isbn);
        return null;
    }

    @Override
    public void validarIsbnUnico(String isbn) throws Exception {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacío");
        }

        System.out.println("Validando ISBN único: " + isbn);

        // VERIFICACIÓN REAL CON LA BASE DE DATOS
        FichaBibliografica existente = buscarPorIsbn(isbn);
        if (existente != null) {
            throw new Exception("El ISBN '" + isbn + "' ya existe en el sistema");
        }

        System.out.println("ISBN único y válido: " + isbn);
    }
}
