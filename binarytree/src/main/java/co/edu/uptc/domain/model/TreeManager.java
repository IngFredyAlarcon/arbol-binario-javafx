package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateTreeException;
import java.util.HashMap;
import java.util.Map;

public class TreeManager {
    private Map<String, BinaryTree> trees;

    public TreeManager() {
        this.trees = new HashMap<>();
    }


    public void createTree(String name) throws DuplicateTreeException {
        String key = name.toLowerCase();
        if (trees.containsKey(key)) {
            throw new DuplicateTreeException("Ya existe un árbol con el nombre: " + name);
        }
        trees.put(key, new BinaryTree(name));
    }

    public void deleteTree(String name) {
        trees.remove(name.toLowerCase());
    }

    public Map<String, BinaryTree> getTrees() {
        return trees;
    }

    public BinaryTree getTree(String name) {
        return trees.get(name.toLowerCase());
    }

    public boolean treeExists(String name) {
        return trees.containsKey(name.toLowerCase());
    }
}