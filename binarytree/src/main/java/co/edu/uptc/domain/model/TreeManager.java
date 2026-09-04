package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.TreeNameTooLongException;
import java.util.HashMap;
import java.util.Map;

public class TreeManager {
    private Map<String, BinaryTree> trees;
    private static final int MAX_NAME_LENGTH = 20;

    public TreeManager() {
        this.trees = new HashMap<>();
    }

    // Crear un nuevo árbol con un nombre específico
    public void createTree(String name) throws DuplicateTreeException, TreeNameTooLongException {
        // Validar que el nombre no sea nulo o vacío
        if (name == null || name.trim().isEmpty()) {
            throw new TreeNameTooLongException("El nombre del árbol no puede estar vacío.");
        }

        // Validar que el nombre no supere los 20 caracteres
        if (name.length() > MAX_NAME_LENGTH) {
            throw new TreeNameTooLongException(
                "El nombre del árbol no puede superar los " + MAX_NAME_LENGTH + 
                " caracteres. Longitud actual: " + name.length()
            );
        }

        // Validar que no exista un árbol con el mismo nombre
        if (trees.containsKey(name)) {
            throw new DuplicateTreeException("Ya existe un árbol con el nombre: " + name);
        }

        trees.put(name, new BinaryTree(name));
    }

    // Método para eliminar un árbol
    public void deleteTree(String name) {
        trees.remove(name);
    }

    // Método para obtener el mapa de árboles
    public Map<String, BinaryTree> getTrees() {
        return trees;
    }

    // Método para obtener un árbol por su nombre
    public BinaryTree getTree(String name) {
        return trees.get(name);
    }

    // Método para verificar si existe un árbol
    public boolean treeExists(String name) {
        return trees.containsKey(name);
    }
}