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

    public Node searchNode(String name,int value) {
        if (name == null || trees.get(name).getRoot() == null) {
            throw new IllegalStateException("No hay un árbol activo para buscar.");
        }
        return trees.get(name).searchNode(value);
    }
    public void deleteTree(String name) {
        trees.remove(name);
    }
    public Map<String, BinaryTree> getTrees() {
        return trees;
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
