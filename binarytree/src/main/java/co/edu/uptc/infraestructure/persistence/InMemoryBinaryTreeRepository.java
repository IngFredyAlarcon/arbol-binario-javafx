package co.edu.uptc.infraestructure.persistence;

import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación temporal en memoria de {@link BinaryTreeRepository}, mientras
 * no exista la persistencia en archivos (RF09/RF10). Se puede reemplazar por
 * una implementación basada en archivos sin afectar a quienes dependan de la
 * interfaz de dominio.
 */
public class InMemoryBinaryTreeRepository implements BinaryTreeRepository {

    private final Map<String, BinarySearchTree> trees = new LinkedHashMap<>();

    public InMemoryBinaryTreeRepository() {
        seedSampleTrees();
    }

    private void seedSampleTrees() {
        BinarySearchTree treeA = new BinarySearchTree();
        treeA.insert(50);
        treeA.insert(30);
        treeA.insert(70);
        treeA.insert(20);
        trees.put("Árbol A", treeA);

        BinarySearchTree treeB = new BinarySearchTree();
        treeB.insert(10);
        treeB.insert(5);
        trees.put("Árbol B", treeB);

        trees.put("Árbol vacío", new BinarySearchTree());
    }

    @Override
    public void save(String name, BinarySearchTree tree) {
        trees.put(name, tree);
    }

    @Override
    public BinarySearchTree findByName(String name) {
        return trees.get(name);
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<>(trees.keySet());
    }

    @Override
    public void delete(String name) {
        trees.remove(name);
    }

    @Override
    public boolean exists(String name) {
        return trees.containsKey(name);
    }
}
