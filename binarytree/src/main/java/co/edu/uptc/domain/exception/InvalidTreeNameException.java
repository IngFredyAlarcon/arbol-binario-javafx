package co.edu.uptc.domain.exception;
//Ocurre cuando el usuario intenta nombrar un arbol dejando el texto vacio o caracteres prohibidos.
public class InvalidTreeNameException extends RuntimeException {
    public InvalidTreeNameException(String name) {
        super("El nombre del árbol es inválido. No puede estar vacío ni contener caracteres especiales.");
    }
}