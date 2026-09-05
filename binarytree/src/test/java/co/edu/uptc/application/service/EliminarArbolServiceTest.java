package co.edu.uptc.application.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.infraestructure.persistence.JsonBinaryTreeRepository;

public class EliminarArbolServiceTest {

    private EliminarArbolService sv;
    private JsonBinaryTreeRepository rep;

    @BeforeEach
    public void setUp() {
        rep = new JsonBinaryTreeRepository() {
            private final Map<String, BinarySearchTree> memory = new HashMap<>();

            @Override public void save(String name, BinarySearchTree tree) { memory.put(name, tree); }
            @Override public BinarySearchTree findByName(String name) { return memory.get(name); }
            @Override public List<String> findAll() { return new ArrayList<>(memory.keySet()); }
            @Override public void delete(String name) { memory.remove(name); }
            @Override public boolean exists(String name) { return memory.containsKey(name); }
        };

        rep.save("Arbol 1", new BinarySearchTree());
        sv = new EliminarArbolService(rep);
    }

    @Test
    public void testEliminarArbolExitoso() {
        assertTrue(rep.exists("Arbol 1"));

        sv.eliminarArbol("Arbol 1");

        assertFalse(rep.exists("Arbol 1"));
        assertFalse(sv.obtenerNombresArboles().contains("Arbol 1"));
    }

    @Test
    public void testEliminarArbolNoSeleccionado() {
        assertThrows(NoTreeSelectedException.class, () -> {
            sv.eliminarArbol(null);
        });
    }
}