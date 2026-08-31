package co.edu.uptc.domain.exception;

public class ValueNotFoundException extends RuntimeException{
    public ValueNotFoundException(int value) {
        super("El valor " + value + " no existe en el árbol.");
    }
}
