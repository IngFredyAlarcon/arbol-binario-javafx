package co.edu.uptc.domain.model;

import co.edu.uptc.infraestructure.persistence.BinaryTreeRepository;
import co.edu.uptc.domain.exception.DuplicateTreeException;
import java.util.HashMap;
import java.util.Map;

public class TreeManager {
    private BinaryTreeRepository repository;
    private Map<String, BinaryTree> trees;

    public TreeManager(BinaryTreeRepository repository) {
        this.repository=repository;
        this.trees = new HashMap<>();
    }

    // Crear un nuevo árbol con un nombre específico
    public void createTree(String name) throws DuplicateTreeException {
        if (trees.containsKey(name)) {
            throw new DuplicateTreeException("Ya existe un árbol con el nombre: " + name);
        }
        saveTree(name, new BinaryTree(name));
    }

    // Método para obtener el mapa de árboles
    public Map<String, BinaryTree> getTrees() {
        return trees;
    }

    //Método para guardar el árbol
    public void saveTree(String name, BinaryTree tree){
        trees.put(name, tree);
        repository.saveList(trees);
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
    public void deleteTree(String name) {
        trees.remove(name);
    }

    public BinaryTree getTree(String name) {
        return trees.get(name);
    }
    public boolean treeExists(String name) {
        return trees.containsKey(name);
    }

    public void setTrees(Map<String, BinaryTree> loadedTrees) {
        if (loadedTrees != null) {
            this.trees = loadedTrees;
        }
    }
}
