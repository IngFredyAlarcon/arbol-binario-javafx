
package co.edu.uptc.domain.exception;
/**
 * Avisa que el arbol con el nombre solicitado no se guardo en la persistencia.
 */

public class TreeNotFoundException extends RuntimeException {
    public TreeNotFoundException(String nombre) {
        super("El árbol " + nombre + " no existe en el sistema.");
    }
}