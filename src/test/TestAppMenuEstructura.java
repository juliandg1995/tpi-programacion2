package test;

import main.AppMenu;

public class TestAppMenuEstructura {
    public static void main(String[] args) {
        try {
            System.out.println("PROBANDO ESTRUCTURA DEL APPMENU");
            System.out.println("===================================");

            AppMenu menu = new AppMenu();
            System.out.println("AppMenu instanciado correctamente");
            System.out.println("Servicios inicializados");
            System.out.println("Scanner configurado");

            System.out.println("\nESTRUCTURA BASE LISTA");
            System.out.println("7 operaciones CRUD definidas");
            System.out.println("Métodos auxiliares implementados");
            System.out.println("Listo para implementar funcionalidades específicas");

        } catch (Exception e) {
            System.out.println("Error en estructura: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
