package co.edu.uptc.infraestructure.exception;
//Ocurre al momento de leer o guardar el archivo JSON si sucedio un fallo tecnico.
public class PersistenceException extends RuntimeException {
    public PersistenceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}