package co.edu.uptc.application.service;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.EmptyTreeException;
import co.edu.uptc.domain.exception.InvalidTreeNameException;
import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.infraestructure.exception.PersistenceException;
import co.edu.uptc.infraestructure.persistence.JsonBinaryTreeRepository;

public class EliminarArbolTest {

    private JsonBinaryTreeRepository repository;
    private EliminarArbolService service;

    @BeforeEach
    public void setUp() {
        // Inicializa el repositorio real
        repository = new JsonBinaryTreeRepository();
        service = new EliminarArbolService(repository);
    }

    @Test
    @DisplayName("Lanza excepción si el nombre enviado a eliminar es nulo")
    public void testEliminarArbolNombreNulo() {
        assertThrows(NoTreeSelectedException.class, () -> {
            service.eliminarArbol(null);
        });
    }

    @Test
    @DisplayName("Lanza excepción si el árbol no existe en el repositorio")
    public void testEliminarArbolNoExistente() {
        String nombreInexistente = "Arbol InexistenteXYZ";
        assertFalse(repository.exists(nombreInexistente));

        assertThrows(NoTreeSelectedException.class, () -> {
            service.eliminarArbol(nombreInexistente);
        });
    }

    @Test
    @DisplayName("Elimina correctamente un árbol existente")
    public void testEliminarArbolExitoso() {
        String nombreExistente = "Arbol 1";

        if (repository.exists(nombreExistente)) {
            assertDoesNotThrow(() -> {
                service.eliminarArbol(nombreExistente);
            });
            assertFalse(repository.exists(nombreExistente));
        }
    }

    @Test
    @DisplayName("Obtiene la lista de nombres de árboles guardados")
    public void testObtenerNombresArboles() {
        List<String> nombres = service.obtenerNombresArboles();
        assertNotNull(nombres);
    }



    @Test
    @DisplayName("Excepción: DuplicateValueException")
    public void testDuplicateValueException() {
        DuplicateValueException ex = assertThrows(DuplicateValueException.class, () -> {
            throw new DuplicateValueException(10);
        });
        assertEquals("El valor 10 ya existe en el árbol.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: EmptyTreeException")
    public void testEmptyTreeException() {
        EmptyTreeException ex = assertThrows(EmptyTreeException.class, () -> {
            throw new EmptyTreeException("eliminar");
        });
        assertEquals("No se puede realizar la operación 'eliminar' porque el árbol está vacío.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: InvalidTreeNameException")
    public void testInvalidTreeNameException() {
        InvalidTreeNameException ex = assertThrows(InvalidTreeNameException.class, () -> {
            throw new InvalidTreeNameException("");
        });
        assertEquals("El nombre del árbol es inválido. No puede estar vacío ni contener caracteres especiales.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: NoTreeSelectedException")
    public void testNoTreeSelectedException() {
        NoTreeSelectedException ex = assertThrows(NoTreeSelectedException.class, () -> {
            throw new NoTreeSelectedException();
        });
        assertEquals("No hay ningún árbol seleccionado. Cree o seleccione uno primero.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: TreeNotFoundException")
    public void testTreeNotFoundException() {
        TreeNotFoundException ex = assertThrows(TreeNotFoundException.class, () -> {
            throw new TreeNotFoundException("ArbolPrueba");
        });
        assertEquals("El árbol ArbolPrueba no existe en el sistema.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: ValueNotFoundException")
    public void testValueNotFoundException() {
        ValueNotFoundException ex = assertThrows(ValueNotFoundException.class, () -> {
            throw new ValueNotFoundException(99);
        });
        assertEquals("El valor 99 no existe en el árbol.", ex.getMessage());
    }

    @Test
    @DisplayName("Excepción: PersistenceException")
    public void testPersistenceException() {
        IOException causa = new IOException("Error de lectura");
        PersistenceException ex = assertThrows(PersistenceException.class, () -> {
            throw new PersistenceException("Error al leer JSON", causa);
        });

        assertEquals("Error al leer JSON", ex.getMessage());
        assertNotNull(ex.getCause());
        assertEquals("Error de lectura", ex.getCause().getMessage());
    }
}