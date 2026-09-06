
package co.edu.uptc.domain.exception;
//Ocurre cuando se solicita informacion a un arbol que no tiene ningun nodo.
public class EmptyTreeException extends RuntimeException {
    public EmptyTreeException(String operacion) {
        super("No se puede realizar la operación '" + operacion + "' porque el árbol está vacío.");
    }
}