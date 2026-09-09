package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private Node root;
    private String name;

    public BinaryTree(String name) {
        this.name = name;
        this.root = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getRoot() {
        return root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // ---------- Insertar (RF02) ----------

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.getValue()) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRecursive(current.getRight(), value));
        } else {
            throw new DuplicateValueException(value);
        }
        return current;
    }

    /**
     * Inicia la búsqueda de un nodo específico en el árbol binario.
     * Es el método público que interactúa con las capas superiores.
     * 
     * @param value El valor entero que se desea buscar.
     * @return El objeto Node que contiene el valor buscado.
     * @throws ValueNotFoundException Si el valor no se encuentra en el árbol.
     */
    public Node searchNode(int value) {
        return searchNodeRecursive(root, value);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol comparando valores.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @param value El valor entero a buscar.
     * @return El objeto Node que coincide con el valor.
     * @throws ValueNotFoundException Si se llega a una rama nula, indicando que el número no existe.
     */
    private Node searchNodeRecursive(Node current, int value) {
        if (current == null) {
            throw new ValueNotFoundException(value);
        }
        if (value == current.getValue()) {
            return current;
        }
        if (value < current.getValue()) {
            return searchNodeRecursive(current.getLeft(), value);
        }
        return searchNodeRecursive(current.getRight(), value);
    }

    // ---------- Buscar (RF03) ----------

    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.getValue()) {
            return true;
        }
        return value < current.getValue()
                ? containsRecursive(current.getLeft(), value)
                : containsRecursive(current.getRight(), value);
    }

    // ---------- Eliminar (RF04) ----------

    public void delete(int value) {
        if (!contains(value)) {
            throw new ValueNotFoundException(value);
        }
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value < current.getValue()) {
            current.setLeft(deleteRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(deleteRecursive(current.getRight(), value));
        } else {
            // Caso 1: nodo hoja
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }
            // Caso 2: un solo hijo
            if (current.getLeft() == null) {
                return current.getRight();
            }
            if (current.getRight() == null) {
                return current.getLeft();
            }
            // Caso 3: dos hijos -> sucesor inorden
            int successorValue = findMin(current.getRight());
            Node replacement = new Node(successorValue);
            replacement.setLeft(current.getLeft());
            replacement.setRight(deleteRecursive(current.getRight(), successorValue));
            return replacement;
        }

        return current;
    }

    // ---------- Recorridos (RF05) ----------

    public List<Integer> preOrder() {
        List<Integer> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node current, List<Integer> result) {
        if (current == null) return;
        result.add(current.getValue());
        preOrderRecursive(current.getLeft(), result);
        preOrderRecursive(current.getRight(), result);
    }

    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node current, List<Integer> result) {
        if (current == null) return;
        inOrderRecursive(current.getLeft(), result);
        result.add(current.getValue());
        inOrderRecursive(current.getRight(), result);
    }

    public List<Integer> postOrder() {
        List<Integer> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node current, List<Integer> result) {
        if (current == null) return;
        postOrderRecursive(current.getLeft(), result);
        postOrderRecursive(current.getRight(), result);
        result.add(current.getValue());
    }

    // ---------- Propiedades (RF07) ----------

    public int size() {
        return countNodes(root);
    }

    private int countNodes(Node current) {
        if (current == null) return 0;
        return 1 + countNodes(current.getLeft()) + countNodes(current.getRight());
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node current) {
        if (current == null) return -1;
        return 1 + Math.max(heightRecursive(current.getLeft()), heightRecursive(current.getRight()));
    }

    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(Node current) {
        if (current == null) return 0;
        if (current.getLeft() == null && current.getRight() == null) return 1;
        return countLeavesRecursive(current.getLeft()) + countLeavesRecursive(current.getRight());
    }

    public int findMin() {
        if (isEmpty()) {
            throw new ValueNotFoundException(Integer.MIN_VALUE);
        }
        return findMin(root);
    }

    private int findMin(Node current) {
        return current.getLeft() == null ? current.getValue() : findMin(current.getLeft());
    }

    public int findMax() {
        if (isEmpty()) {
            throw new ValueNotFoundException(Integer.MAX_VALUE);
        }
        return findMax(root);
    }

    private int findMax(Node current) {
        return current.getRight() == null ? current.getValue() : findMax(current.getRight());
    }

    // ---------- Limpiar (RF08) ----------

    public void clear() {
        root = null;
    }
}
