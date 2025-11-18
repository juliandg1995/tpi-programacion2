package main;

/**
 * Clase principal que inicia la aplicación del Sistema de Gestión Bibliotecaria
 * Punto de entrada del Trabajo Práctico Integrador de Programación 2
 */
public class Main {
    public static void main(String[] args) {
        try {
            AppMenu menu = new AppMenu();
            menu.iniciar();
        } catch (Exception e) {
            System.out.println("Error crítico al iniciar la aplicación: " + e.getMessage());
            System.out.println("Verifique la configuración de la base de datos");
        }
    }
}
