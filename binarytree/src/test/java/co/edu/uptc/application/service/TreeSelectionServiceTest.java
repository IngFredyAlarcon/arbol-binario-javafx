package co.edu.uptc.application.service;

import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TreeSelectionServiceTest {

    private TreeManager treeManager;
    private TreeSelectionService service;

    @BeforeEach
    void setUp() {
        treeManager = new TreeManager();
        service = new TreeSelectionService(treeManager);
    }

    @Test
    void shouldReturnEmptyListWhenNoTreesStored() {
        assertTrue(service.getAvailableTreeNames().isEmpty());
    }

    @Test
    void shouldListAvailableTreeNames() throws Exception {
        treeManager.createTree("A");
        treeManager.createTree("B");

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
    void shouldAllowChangingSelectionBetweenTrees() throws Exception {
        treeManager.createTree("A");
        treeManager.createTree("B");

        service.selectTree("A");
        service.selectTree("B");

        assertEquals("B", service.getSelectedTreeName());
    }

    @Test
    void shouldLoadTheActualContentOfTheSelectedTree() throws Exception {
        treeManager.createTree("Balanceado");
        treeManager.insertValue("Balanceado", 50);
        treeManager.insertValue("Balanceado", 30);
        treeManager.insertValue("Balanceado", 70);

        treeManager.createTree("Vacio");

        service.selectTree("Balanceado");
        BinaryTree loaded = service.getSelectedTree();

        assertNotNull(loaded);
        assertFalse(loaded.isEmpty());
        assertEquals(50, loaded.getRoot().getValue());

        service.selectTree("Vacio");
        assertTrue(service.getSelectedTree().isEmpty());
    }
}
