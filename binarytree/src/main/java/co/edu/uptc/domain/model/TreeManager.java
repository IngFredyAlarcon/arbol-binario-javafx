package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateTreeException;
import java.util.HashMap;
import java.util.Map;

public class TreeManager {
    private Map<String, BinaryTree> trees;

    public TreeManager() {
        this.trees = new HashMap<>();
    }

    // Crear un nuevo árbol con un nombre específico
    public void createTree(String name) throws DuplicateTreeException {
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
