package co.edu.uptc.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BinaryTreeTest {

    @Test
    @DisplayName("Verificar recorridos del árbol (PreOrder, InOrder, PostOrder, LevelOrder)")
    void pruebaRecorrido() {
    Node root = new Node(10);
    root.setLeft(new Node(5));
    root.setRight(new Node(15));
    root.getLeft().setLeft(new Node(2));
    root.getLeft().setRight(new Node(7));

    BinaryTree tree = new BinaryTree("Árbol Test");
    tree.setRoot(root);

    assertEquals(List.of(10, 5, 2, 7, 15), tree.getPreOrder());
    assertEquals(List.of(2, 5, 7, 10, 15), tree.getInOrder());
    assertEquals(List.of(2, 7, 5, 15, 10), tree.getPostOrder());
    assertEquals(List.of(10, 5, 15, 2, 7), tree.getLevelOrder());
}
}