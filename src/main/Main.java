
package main;

/**
 * @author Julian Daniel Gómez <https://github.com/juliandg1995>
 * Punto de entrada de la aplicación.
 * Solo delega en AppMenu.
 */

public class Main{

    public static void main(String[] args){
        AppMenu menu = new AppMenu();
        menu.iniciar();
    }
}