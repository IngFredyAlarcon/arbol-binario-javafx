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
            private Map<String, BinarySearchTree> memory;

            private Map<String, BinarySearchTree> getMemory() {
                if (memory == null) {
                    memory = new HashMap<>();
                }
                return memory;
            }

            @Override public void save(String name, BinarySearchTree tree) { getMemory().put(name, tree); }
            @Override public BinarySearchTree findByName(String name) { return getMemory().get(name); }
            @Override public List<String> findAll() { return new ArrayList<>(getMemory().keySet()); }
            @Override public void delete(String name) { getMemory().remove(name); }
            @Override public boolean exists(String name) { return getMemory().containsKey(name); }
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