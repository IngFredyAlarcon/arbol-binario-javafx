package co.edu.uptc.domain.exception;
/**
 * Ocurre cuando el usuario presiona una accion como eliminar sin haber seleccionado
 * antes ningun arbol en la pantalla.
 */

public class NoTreeSelectedException extends RuntimeException {

    public NoTreeSelectedException() {
        super("No hay ningún árbol seleccionado. Cree o seleccione uno primero.");
    }
}