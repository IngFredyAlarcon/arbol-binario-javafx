package co.edu.uptc.domain.exception;

public class NoTreeSelectedException extends RuntimeException {

    public NoTreeSelectedException() {
        super("No hay ningún árbol seleccionado. Cree o seleccione uno primero.");
    }
}