package service;

import entities.Libro;
import entities.FichaBibliografica;
import dao.GenericDao;
import java.sql.Connection;
import java.util.List;

public class LibroServiceImpl implements LibroService {

    // TODO: Inyectar dependencias (DAOs)
    // private LibroDAO libroDAO;
    // private FichaBibliograficaService fichaService;
    // PREPARADO para inyección de DAOs
    private GenericDao<Libro> libroDAO;
    private GenericDao<FichaBibliografica> fichaDAO;


    public LibroServiceImpl() {
        // TODO: Inicializar dependencias
        // Por ahora vacío - los DAOs se inyectarán después
    }


    // SETTERS para inyección de dependencias
    public void setLibroDAO(GenericDao<Libro> libroDAO) {
        this.libroDAO = libroDAO;
    }

    public void setFichaDAO(GenericDao<FichaBibliografica> fichaDAO) {
        this.fichaDAO = fichaDAO;
    }

    // --- MÉTODOS HEREDADOS DE GenericService ---
    @Override
    public void insertar(Libro libro) throws Exception {
        // TODO: Validaciones + llamar a libroDAO.crear()
        System.out.println("Insertar libro: " + libro.getTitulo());
    }

    @Override
    public void actualizar(Libro libro) throws Exception {
        // TODO: Validaciones + llamar a libroDAO.actualizar()
        System.out.println("Actualizar libro: " + libro.getTitulo());
    }

    @Override
    public void eliminar(Long id) throws Exception {
        // TODO: Lógica de baja (física o lógica)
        System.out.println("Eliminar libro ID: " + id);
    }

    @Override
    public Libro buscarPorId(Long id) throws Exception {
        // TODO: Llamar a libroDAO.leer(id)
        System.out.println("Buscar libro por ID: " + id);
        return null; // Temporal
    }

    @Override
    public List<Libro> listarTodos() throws Exception {
        System.out.println("Listando todos los libros");

        // CUANDO EL DAO ESTÉ LISTO:
        // return libroDAO.leerTodos();

        // SIMULACIÓN TEMPORAL - retorna lista vacía
        // En un futuro: return libroDAO.leerTodos();

        System.out.println("Listado completado - 0 libros (simulación)");
        return java.util.Collections.emptyList(); // Lista vacía inmutable
    }

    // --- MÉTODOS ESPECÍFICOS DE LibroService ---
    @Override
    public void crearLibroConFicha(Libro libro, FichaBibliografica ficha) throws Exception {
        // ESTRUCTURA lista para DAOs reales
        Connection conexion = null;
        try {
            // TODO: Obtener conexión de DatabaseConnection
            // conexion = DatabaseConnection.getConnection();
            // conexion.setAutoCommit(false);

            // SIMULACIÓN TEMPORAL con prints
            System.out.println("🔹 Iniciando transacción para libro: " + libro.getTitulo());

            // CUANDO LOS DAOs ESTÉN, SERÁ ASÍ:
            // fichaDAO.crear(ficha); // Con conexión transaccional
            // libroDAO.crear(libro); // Con conexión transaccional

            System.out.println("Ficha creada: " + ficha.getIsbn());
            System.out.println("Libro creado: " + libro.getTitulo());

            // CUANDO ESTÉ LISTO:
            // conexion.commit();
            System.out.println("Transacción SIMULADA exitosa");

        } catch (Exception error) {
            // CUANDO ESTÉ LISTO:
            // if (conexion != null) conexion.rollback();
            System.out.println("Transacción fallida - Rollback SIMULADO");
            throw new Exception("Error en transacción: " + error.getMessage(), error);

        } finally {
            // CUANDO ESTÉ LISTO:
            // if (conexion != null) {
            //     conexion.setAutoCommit(true);
            //     conexion.close();
            // }
        }
    }

    @Override
    public void asignarFichaBibliografica(Long idLibro, FichaBibliografica ficha) throws Exception {
        System.out.println("Asignar ficha al libro ID: " + idLibro);
        // TODO: Validar que el libro existe + asignar ficha
    }

    @Override
    public Libro buscarPorTitulo(String titulo) throws Exception {
        System.out.println("Buscar libro por título: " + titulo);
        return null; // Temporal
    }

    @Override
    public boolean existeLibroConTitulo(String titulo) throws Exception {
        System.out.println("Verificar existencia de título: " + titulo);
        return false; // Temporal
    }
}
