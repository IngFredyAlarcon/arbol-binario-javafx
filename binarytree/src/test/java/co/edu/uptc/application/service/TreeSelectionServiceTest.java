package co.edu.uptc.application.service;

import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeSelectionServiceTest {

    private BinaryTreeRepository repository;
    private TreeSelectionService service;

    @BeforeEach
    void setUp() {
        repository = new FakeBinaryTreeRepository();
        service = new TreeSelectionService(repository);
    }


    @Test
    void shouldReturnEmptyListWhenNoTreesStored() {
        assertTrue(service.getAvailableTreeNames().isEmpty());
    }

    @Test
    void shouldListAvailableTreeNames() {
        repository.save("A", new BinarySearchTree());
        repository.save("B", new BinarySearchTree());

        List<String> names = service.getAvailableTreeNames();

        assertEquals(2, names.size());
        assertTrue(names.contains("A"));
        assertTrue(names.contains("B"));
    }


    @Test
    void shouldThrowWhenSelectingNonExistentTree() {
        assertThrows(TreeNotFoundException.class, () -> service.selectTree("Fantasma"));
    }

    @Test
    void shouldAllowChangingSelectionBetweenTrees() {
        repository.save("A", new BinarySearchTree());
        repository.save("B", new BinarySearchTree());

        service.selectTree("A");
        service.selectTree("B");

        assertEquals("B", service.getSelectedTreeName());
    }

    private static class FakeBinaryTreeRepository implements BinaryTreeRepository {
        private final Map<String, BinarySearchTree> trees = new HashMap<>();

        @Override
        public void save(String name, BinarySearchTree tree) {
            trees.put(name, tree);
        }

        @Override
        public BinarySearchTree findByName(String name) {
            return trees.get(name);
        }

        @Override
        public List<String> findAll() {
            return new ArrayList<>(trees.keySet());
        }

        @Override
        public void delete(String name) {
            trees.remove(name);
        }

        @Override
        public boolean exists(String name) {
            return trees.containsKey(name);
        }
    }
}
