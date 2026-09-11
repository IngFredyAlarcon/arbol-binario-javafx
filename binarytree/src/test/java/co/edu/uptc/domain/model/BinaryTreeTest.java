package co.edu.uptc.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BinaryTreeTest {

    @Test
    @DisplayName("Verificar recorridos del árbol (PreOrder, InOrder, PostOrder, LevelOrder)")
    void pruebaRecorrido() {
        BinaryTree tree = new BinaryTree("Árbol Test");
        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(2);
        tree.insert(7);

        assertEquals(List.of(10, 5, 2, 7, 15), tree.getPreOrder());
        assertEquals(List.of(2, 5, 7, 10, 15), tree.getInOrder());
        assertEquals(List.of(2, 7, 5, 15, 10), tree.getPostOrder());
        assertEquals(List.of(10, 5, 15, 2, 7), tree.getLevelOrder());
    }
}