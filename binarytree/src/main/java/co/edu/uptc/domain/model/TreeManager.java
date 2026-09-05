package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

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

    public void insertValue(String name,int value) {
        if (name == null) {
            throw new IllegalStateException("Primero debes crear un árbol.");
        }
        trees.get(name).insert(value);;
    }

    /**
     * Coordina la operación de búsqueda validando primero el estado del sistema.
     * Delega la responsabilidad de la búsqueda matemática a la capa de dominio.
     * 
     * @param value El valor numérico ingresado por el usuario.
     * @return El nodo encontrado devuelto por el árbol.
     * @throws IllegalStateException Si se intenta buscar sin haber creado un árbol primero.
     * @throws ValueNotFoundException Si el número no pertenece al árbol.
     */
    public Node searchNode(String name,int value) {
        if (name == null || trees.get(name).getRoot() == null) {
            throw new IllegalStateException("No hay un árbol activo para buscar.");
        }
        return trees.get(name).searchNode(value);
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
