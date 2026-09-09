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

    // Método para eliminar un árbol
    public void deleteTree(String name) {
        trees.remove(name);
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

    public BinaryTree getTree(String name) {
        return trees.get(name.toLowerCase());
    }

    public boolean treeExists(String name) {
        return trees.containsKey(name.toLowerCase());
    }
}
