package co.edu.uptc.domain.model;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BinarySearchTreeDeleteTest {

    // Caso 1: eliminar un nodo que es hoja (no tiene hijos)
    @Test
    void deleteLeafNode() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(20);

        tree.delete(5);

        assertEquals(false, tree.contains(5));
        assertEquals(List.of(10, 20), tree.inOrder());
    }

    // Caso 2: eliminar un nodo que solo tiene hijo derecho
    @Test
    void deleteNodeWithOnlyRightChild() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(25);

        tree.delete(20);

        assertEquals(false, tree.contains(20));
        assertEquals(List.of(10, 25), tree.inOrder());
    }

    // Caso 3: eliminar un nodo que solo tiene hijo izquierdo
    @Test
    void deleteNodeWithOnlyLeftChild() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(10);
        tree.insert(5);
        tree.insert(3);

        tree.delete(5);

        assertEquals(false, tree.contains(5));
        assertEquals(List.of(3, 10), tree.inOrder());
    }

    // Caso 4: eliminar un nodo con dos hijos (se reemplaza por el sucesor)
    @Test
    void deleteNodeWithTwoChildren() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        tree.delete(50);

        assertEquals(false, tree.contains(50));
        assertEquals(List.of(20, 30, 40, 60, 70, 80), tree.inOrder());
    }
}