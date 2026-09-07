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

    /**
     * Halla el numero de nodos del arbol, por medio de una funcion recursiva.
     * 
     * @return El numero de nodos del arbol.
     */
    public int getSize() {
        return getSizeRecursive(root);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol contando cada nodo.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @return El numero de nodos del arbol.
     */
    private int getSizeRecursive(Node current) {
        if (current == null) {
            return 0;
        }

        return 1 + getSizeRecursive(current.getLeft()) + getSizeRecursive(current.getRight());
    }

    /**
     * Halla la altura del arbol, por medio de una funcion recursiva.
     * 
     * @return La altura del arbol.
     */
    public int getHeight() {
        return getHeightRecursive(root);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol hasta hallar la altura del nodo.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @return La altura del arbol desde el nodo indicado {@param current}.
     */
    private int getHeightRecursive(Node current) {
        if (current == null) {
            return 0;
        }

        return Math.max(
            getHeightRecursive( current.getLeft() ) + 1,
            getHeightRecursive( current.getRight() ) + 1
        );
    }

    /**
     * Halla la cantidad de hojas del arbol, por medio de una funcion recursiva.
     * 
     * @return La altura del arbol.
     */
    public int getLeavesSize() {
        return getLeavesSizeRecursive(root);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol contando las hojas del arbol.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @return La cantidad de hojas del arbol que sale del nodo current{@param current}.
     */
    private int getLeavesSizeRecursive(Node current) {
        if (current == null) {
            return 0;
        }

        return current.getLeft() == null && current.getRight() == null ?
            1 : getLeavesSizeRecursive(current.getLeft()) + getLeavesSizeRecursive(current.getRight());
    }

    /**
     * Halla el valor maximo, por medio de una funcion recursiva.
     * 
     * @return El valor maximo del arbol.
     */
    public int getMaxValue() {
        return getMaxValueRecursive(root);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol hasta hallar el valor maximo del arbol.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @return El valor maximo del arbol dado desde el nodo indicado{@param current}.
     * @throws IllegalStateException Si el arbol esta vacio.
     */
    private Integer getMaxValueRecursive(Node current) {
        if (current == null) {
            throw new IllegalStateException("Primero debes crear un árbol.");
        }
        
        if (current.getRight() == null) {
            return current.getValue();
        } else {
            return  getMaxValueRecursive(current.getRight());
        }
    }

    /**
     * Halla el valor maximo, por medio de una funcion recursiva.
     * 
     * @return El valor maximo del arbol.
     */
    public int getMinValue() {
        return getMinValueRecursive(root);
    }
    
    /**
     * Método auxiliar recursivo que navega por las ramas del árbol hasta hallar el valor minimo del arbol.
     * 
     * @param current El nodo actual que se está evaluando (comienza en la raíz).
     * @return El valor minimo del arbol dado desde el nodo indicado{@param current}.
     * @throws IllegalStateException Si el arbol esta vacio.
     */
    private Integer getMinValueRecursive(Node current) {
        if (current == null) {
            throw new IllegalStateException("Primero debes crear un árbol.");
        }
        
        if (current.getLeft() == null) {
            return current.getValue();
        } else {
            return getMinValueRecursive(current.getLeft());
        }
    }

    public Node getRoot() {
        return root;
    }
}
