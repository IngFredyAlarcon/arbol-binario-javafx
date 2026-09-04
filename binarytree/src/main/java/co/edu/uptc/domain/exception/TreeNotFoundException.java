package co.edu.uptc.domain.exception;

public class TreeNotFoundException extends RuntimeException{
    public TreeNotFoundException(String name) {
        super("El árbol '" + name + "' no existe.");
    }
}
