package service;

import entities.Libro;
import entities.FichaBibliografica;
import service.validations.ValidacionService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import dao.LibroDao;
import dao.FichaBibliograficaDao;
import config.DatabaseConnection;

public class LibroServiceImpl implements LibroService {


    private FichaBibliograficaService fichaService;
    private LibroDao libroDAO;
    private FichaBibliograficaDao fichaDAO;

    // Constructor con inicialización de DAOs
    public LibroServiceImpl() {
        this.libroDAO = new LibroDao();
        this.fichaDAO = new FichaBibliograficaDao();
    }

    // SETTERS para inyección de dependencias
    public void setLibroDAO(LibroDao libroDAO) {
        this.libroDAO = libroDAO;
    }

    public void setFichaDAO(FichaBibliograficaDao fichaDAO) {
        this.fichaDAO = fichaDAO;
    }

    public void setFichaService(FichaBibliograficaService fichaService) {
        this.fichaService = fichaService;
    }


    // --- MÉTODOS HEREDADOS DE GenericService ---

    @Override
    public Libro crear(Libro libro) throws Exception {
        ValidacionService.validarLibro(libro);
        System.out.println("Creando libro: " + libro.getTitulo());

        // LLAMADA AL DAO DE SANDRA
        //Libro libroCreado = libroDAO.crear(libro);

        //System.out.println("Libro creado correctamente - ID: " + libroCreado.getId());
        //return libroCreado;

        return null; // Temporal
    }

    @Override
    public void actualizar(Libro libro) throws Exception {
        ValidacionService.validarLibro(libro);
        System.out.println("Actualizando libro: " + libro.getTitulo());

        // LLAMADA AL DAO DE SANDRA
        //libroDAO.actualizar(libro);

        System.out.println("Libro actualizado correctamente - ID: " + libro.getId());
    }

    @Override
    public void eliminar(Long id) throws Exception {
        ValidacionService.validarId(id, "libro"); // ← NUEVA VALIDACIÓN
        System.out.println("🗑️ Eliminando libro ID: " + id);

        // LLAMADA AL DAO DE SANDRA
        // libroDAO.eliminar(id);

        System.out.println("Libro eliminado correctamente ID: " + id);
    }

    @Override
    public Libro buscarPorId(Long id) throws Exception {
        ValidacionService.validarId(id, "libro");
        System.out.println("🔍 Buscando libro por ID: " + id);


        Libro libro = libroDAO.leer(id);

        if (libro == null) {
            throw new Exception("No se encontró libro con ID: " + id);
        }

        System.out.println("✅ Libro encontrado: " + libro.getTitulo());
        return libro;
    }

    @Override
    public List<Libro> listarTodos() throws Exception {
        System.out.println("📚 Listando todos los libros");

        // USA el DAO de Julian que ya incluye las fichas
        List<Libro> libros = libroDAO.leerTodos();  // ← Ya trae libros + fichas!

        System.out.println("✅ " + libros.size() + " libros encontrados");
        return libros;
    }

    // --- MÉTODOS ESPECÍFICOS DE LibroService ---

    /*@Override
    public void crearLibroConFicha(Libro libro, FichaBibliografica ficha) throws Exception {
        System.out.println("INICIANDO TRANSACCIÓN: Crear Libro con Ficha Bibliográfica");

        // VALIDACIONES CENTRALIZADAS
        ValidacionService.validarLibro(libro);
        ValidacionService.validarFichaBibliografica(ficha);
        ValidacionService.validarIsbnUnico(ficha.getIsbn());
        ValidacionService.validarTituloUnico(libro.getTitulo());

        System.out.println("  Libro: " + libro.getTitulo());
        System.out.println("  Ficha ISBN: " + ficha.getIsbn());

        // ESTRUCTURA TRANSACCIONAL
        Connection conn = null;
        boolean operacionExitosa = false;

        try {
            // SIMULACIÓN: Obtener conexión
            // conn = DatabaseConnection.getConnection();
            // conn.setAutoCommit(false);
            System.out.println("   Conexión establecida - AutoCommit: false");

            // 1. CREAR FICHA BIBLIOGRÁFICA
            System.out.println("   Insertando ficha bibliográfica...");
            // FichaBibliografica fichaCreada = fichaDAO.crear(ficha, conn);
            FichaBibliografica fichaCreada = ficha; // Simulación

            // 2. ASIGNAR FICHA AL LIBRO
            libro.setFichaBibliografica(fichaCreada);
            System.out.println("   Ficha asignada al libro");

            // 3. CREAR LIBRO
            System.out.println("   Insertando libro...");
            // Libro libroCreado = libroDAO.crear(libro, conn);
            Libro libroCreado = libro; // Simulación

            // 4. CONFIRMAR TRANSACCIÓN
            // conn.commit();
            System.out.println("   TRANSACCIÓN EXITOSA - Commit realizado");
            operacionExitosa = true;

            System.out.println("Libro creado exitosamente con ID: " + libroCreado.getId());
            System.out.println("Ficha creada exitosamente con ISBN: " + fichaCreada.getIsbn());

        } catch (Exception error) {
            // 5. REVERTIR EN CASO DE ERROR
            System.out.println("   ERROR en transacción: " + error.getMessage());

            // if (conn != null) {
            //     conn.rollback();
            //     System.out.println("   Rollback ejecutado - Base de datos restaurada");
            // }
            System.out.println("   Rollback SIMULADO - Base de datos restaurada");

            throw new Exception("Error al crear libro con ficha: " + error.getMessage(), error);

        } finally {
            // 6. LIMPIAR RECURSOS
            // if (conn != null) {
            //     try {
            //         conn.setAutoCommit(true);
            //         conn.close();
            //         System.out.println("   Conexión cerrada - AutoCommit: true");
            //     } catch (SQLException e) {
            //         System.err.println("   Error al cerrar conexión: " + e.getMessage());
            //     }
            // }
            System.out.println("   Limpieza de recursos completada");

            // Log del resultado final
            if (operacionExitosa) {
                System.out.println("TRANSACCIÓN COMPLETADA - Estado: ÉXITO");
            } else {
                System.out.println("TRANSACCIÓN COMPLETADA - Estado: FALLIDA");
            }
        }
    }*/

    @Override
    public void crearLibroConFicha(Libro libro, FichaBibliografica ficha) throws Exception {
        System.out.println("🚀 INICIANDO TRANSACCIÓN: Crear Libro con Ficha");

        // Validaciones
        ValidacionService.validarLibro(libro);
        ValidacionService.validarFichaBibliografica(ficha);
        // ValidacionService.validarIsbnUnico(ficha.getIsbn()); // ← Temporalmente comentado

        Connection conn = null;
        boolean operacionExitosa = false;

        try {
            // 1. OBTENER CONEXIÓN
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 2. CREAR LIBRO (con el DAO de Julian)
            System.out.println("   Insertando libro...");
            libroDAO.crear(libro, conn);  // ← Julian actualiza el ID automáticamente

            // 3. ASIGNAR MISMO ID A FICHA (PK Compartida)
            ficha.setId(libro.getId());  // ← Toma el ID generado para el libro
            System.out.println("   Asignando mismo ID a ficha: " + libro.getId());

            // 4. CREAR FICHA (con el DAO de Julian)
            System.out.println("   nsertando ficha bibliográfica...");
            fichaDAO.crear(ficha, conn);

            // 5. ESTABLECER RELACIÓN EN MEMORIA
            libro.setFichaBibliografica(ficha);

            // 6. CONFIRMAR TRANSACCIÓN
            conn.commit();
            System.out.println("   ✅ TRANSACCIÓN EXITOSA - Commit realizado");
            operacionExitosa = true;

            System.out.println("🎉 Libro creado con ID: " + libro.getId());
            System.out.println("🎉 Ficha creada con ISBN: " + ficha.getIsbn());

        } catch (Exception error) {
            // 7. REVERTIR EN CASO DE ERROR
            System.out.println("   ❌ ERROR en transacción: " + error.getMessage());
            if (conn != null) {
                conn.rollback();
                System.out.println("   🔄 Rollback ejecutado");
            }
            throw new Exception("Error al crear libro con ficha: " + error.getMessage(), error);

        } finally {
            // 8. LIMPIAR RECURSOS
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("   ⚠️ Error al cerrar conexión: " + e.getMessage());
                }
            }

            System.out.println(operacionExitosa ? "🏁 TRANSACCIÓN EXITOSA" : "💥 TRANSACCIÓN FALLIDA");
        }
    }


    @Override
    public void asignarFichaBibliografica(Long idLibro, FichaBibliografica ficha) throws Exception {
        if (idLibro == null || idLibro <= 0) {
            throw new IllegalArgumentException("ID de libro inválido");
        }
        if (ficha == null) {
            throw new IllegalArgumentException("La ficha bibliográfica no puede ser nula");
        }

        ValidacionService.validarFichaBibliografica(ficha);

        System.out.println("Asignando ficha a libro existente...");
        System.out.println("   Libro ID: " + idLibro);
        System.out.println("   Ficha ISBN: " + ficha.getIsbn());

        // SIMULACIÓN DE TRANSACCIÓN
        // 1. Verificar que el libro existe
        // Libro libroExistente = libroDAO.leer(idLibro);
        // if (libroExistente == null) {
        //     throw new Exception("Libro con ID " + idLibro + " no encontrado");
        // }

        // 2. Validar ISBN único
        // fichaService.validarIsbnUnico(ficha.getIsbn());

        // 3. Crear ficha y asignar
        // FichaBibliografica fichaCreada = fichaDAO.crear(ficha);
        // libroExistente.setFichaBibliografica(fichaCreada);
        // libroDAO.actualizar(libroExistente);

        System.out.println("Ficha asignada exitosamente al libro ID: " + idLibro);
    }

    @Override
    public Libro buscarPorTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        System.out.println("Buscando libro por título: '" + titulo + "'");

        // SIMULACIÓN TEMPORAL - cuando el DAO esté listo:
        // return libroDAO.buscarPorTitulo(titulo);

        // Simulación con datos de prueba:
        if (titulo.equalsIgnoreCase("Cien años de soledad")) {
            Libro libroSimulado = new Libro();
            libroSimulado.setId(1L);
            libroSimulado.setTitulo("Cien años de soledad");
            libroSimulado.setAutor("Gabriel García Márquez");
            libroSimulado.setEditorial("Sudamericana");
            libroSimulado.setAnioEdicion(1967);
            System.out.println("Libro encontrado: " + titulo);
            return libroSimulado;

        } else if (titulo.equalsIgnoreCase("El Quijote")) {
            Libro libroSimulado = new Libro();
            libroSimulado.setId(2L);
            libroSimulado.setTitulo("El Quijote");
            libroSimulado.setAutor("Miguel de Cervantes");
            libroSimulado.setEditorial("Juan de la Cuesta");
            libroSimulado.setAnioEdicion(1605);
            System.out.println("Libro encontrado: " + titulo);
            return libroSimulado;

        } else {
            System.out.println("Libro NO encontrado: " + titulo);
            return null;
        }
    }

    @Override
    public boolean existeLibroConTitulo(String titulo) throws Exception {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }

        System.out.println("Verificando existencia de título: '" + titulo + "'");

        // Usamos nuestra nueva búsqueda
        Libro libro = buscarPorTitulo(titulo);
        boolean existe = (libro != null);

        System.out.println("Resultado: " + (existe ? "EXISTE" : "NO EXISTE"));
        return existe;
    }
}
