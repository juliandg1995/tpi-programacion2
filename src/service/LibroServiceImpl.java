package service;

import entities.Libro;
import entities.FichaBibliografica;

public class LibroServiceImpl implements LibroService {

    // TODO: Inyectar dependencias (DAOs)
    // private LibroDAO libroDAO;
    // private FichaBibliograficaService fichaService;

    public LibroServiceImpl() {
        // TODO: Inicializar dependencias
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

    // --- MÉTODOS ESPECÍFICOS DE LibroService ---
    @Override
    public void crearLibroConFicha(Libro libro, FichaBibliografica ficha) throws Exception {
        // MÉTODO TRANSACCIONAL
        System.out.println("Crear libro con ficha: " + libro.getTitulo());
        // TODO: setAutoCommit(false), try-catch, commit/rollback
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
