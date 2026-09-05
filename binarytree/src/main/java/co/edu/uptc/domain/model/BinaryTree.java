package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

public class BinaryTree {
    private Node root;
    private String name;

    public BinaryTree(String name) {
        this.name = name;
        root = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Node getRoot() {
        return root;
    }
}
