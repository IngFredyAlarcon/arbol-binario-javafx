package co.edu.uptc.domain.exception;

public class DuplicateValueException extends RuntimeException{
    public DuplicateValueException(int value) {
        super("El valor " + value + " ya existe en el árbol.");
    }
}
