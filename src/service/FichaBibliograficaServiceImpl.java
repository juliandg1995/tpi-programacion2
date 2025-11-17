package service;

import entities.FichaBibliografica;
//import dao.FichaBibliograficaDAO;
import java.sql.Connection;
import java.util.List;
import service.validations.ValidacionService;

public class FichaBibliograficaServiceImpl implements FichaBibliograficaService {

    //private FichaBibliograficaDAO fichaDAO;


    public FichaBibliograficaServiceImpl() {}

    // SETTER para inyección de dependencias
//    public void setFichaDAO(FichaBibliograficaDao fichaDAO) {
//        this.fichaDAO = fichaDAO;
//    }

    // --- MÉTODOS HEREDADOS DE GenericService ---

    @Override
    public FichaBibliografica crear(FichaBibliografica ficha) throws Exception {
        ValidacionService.validarFichaBibliografica(ficha);
        ValidacionService.validarIsbnUnico(ficha.getIsbn());

        System.out.println("Creando ficha bibliográfica - ISBN: " + ficha.getIsbn());

        // LLAMADA AL DAO DE JULIÁN
        //fichaDAO.crear(ficha);

        System.out.println("Ficha creada correctamente: " + ficha.getIsbn());
        return ficha;
    }

    @Override
    public void actualizar(FichaBibliografica ficha) throws Exception {
        ValidacionService.validarFichaBibliografica(ficha);
        System.out.println("Actualizando ficha bibliográfica - ISBN: " + ficha.getIsbn());

        // LLAMADA AL DAO DE JULIÁN
        //fichaDAO.actualizar(ficha);

        System.out.println("Ficha actualizada correctamente: " + ficha.getIsbn());
    }

    @Override
    public void eliminar(Long id) throws Exception {
        ValidacionService.validarId(id, "ficha bibliográfica");

        System.out.println("🗑️ Eliminando ficha bibliográfica ID: " + id);

        // LLAMADA AL DAO DE JULIÁN
        //fichaDAO.eliminar(id);

        System.out.println("Ficha eliminada correctamente ID: " + id);
    }

    @Override
    public FichaBibliografica buscarPorId(Long id) throws Exception {
        ValidacionService.validarId(id, "ficha bibliográfica");
        System.out.println("Buscando ficha por ID: " + id);

        // LLAMADA AL DAO DE JULIÁN
        //FichaBibliografica ficha = fichaDAO.leer(id);

        //if (ficha == null) {
            //throw new Exception("No se encontró ficha con ID: " + id);
       // }

        //System.out.println("Ficha encontrada - ID: " + id + ", ISBN: " + ficha.getIsbn());
        //return ficha;
        return null; // Temporal
    }

    @Override
    public List<FichaBibliografica> listarTodos() throws Exception {
        System.out.println("Listando todas las fichas bibliográficas");

        // LLAMADA AL DAO DE JULIÁN
        //List<FichaBibliografica> fichas = fichaDAO.leerTodos();

        //System.out.println("Listado completado - " + fichas.size() + " fichas encontradas");
        //return fichas;
        return null; // Temporal
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

        // SIMULACIÓN TEMPORAL - cuando el DAO esté listo:
        // FichaBibliografica ficha = fichaDAO.buscarPorIsbn(isbn);
        // if (ficha == null) {
        //     throw new Exception("No se encontró ficha con ISBN: " + isbn);
        // }
        // return ficha;

        // Por ahora simulamos diferentes escenarios:
        if (isbn.equals("978-1234567890")) {
            FichaBibliografica fichaSimulada = new FichaBibliografica();
            fichaSimulada.setId(1L);
            fichaSimulada.setIsbn(isbn);
            fichaSimulada.setClasificacionDewey("025.4");
            fichaSimulada.setEstanteria("A25");
            fichaSimulada.setIdioma("Español");
            System.out.println("Ficha encontrada: " + isbn);
            return fichaSimulada;
        } else if (isbn.equals("978-0987654321")) {
            FichaBibliografica fichaSimulada = new FichaBibliografica();
            fichaSimulada.setId(2L);
            fichaSimulada.setIsbn(isbn);
            fichaSimulada.setClasificacionDewey("028.5");
            fichaSimulada.setEstanteria("B12");
            fichaSimulada.setIdioma("Inglés");
            System.out.println("Ficha encontrada: " + isbn);
            return fichaSimulada;
        } else {
            System.out.println("Ficha NO encontrada: " + isbn);
            return null; // Simula que no se encontró
        }
    }

    @Override
    public void validarIsbnUnico(String isbn) throws Exception {
        ValidacionService.validarIsbnUnico(isbn);

        System.out.println("Validando ISBN único: " + isbn);

        // CUANDO EL DAO ESTÉ LISTO:
        // FichaBibliografica existente = buscarPorIsbn(isbn);
        // if (existe != null) {
        //     throw new Exception("El ISBN '" + isbn + "' ya existe en el sistema");
        // }

        // Simulación temporal:
        if (isbn.equals("978-1234567890") || isbn.equals("978-0987654321")) {
            throw new Exception("El ISBN '" + isbn + "' ya existe en el sistema (simulación)");
        }

        System.out.println("ISBN único y válido: " + isbn);
    }
}
