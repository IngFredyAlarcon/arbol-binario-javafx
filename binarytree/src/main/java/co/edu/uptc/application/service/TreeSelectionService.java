package co.edu.uptc.application.service;

import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;

import java.util.List;

public class TreeSelectionService {

    private final BinaryTreeRepository repository;
    private String selectedTreeName;

    public TreeSelectionService(BinaryTreeRepository repository) {
        this.repository = repository;
    }

    public List<String> getAvailableTreeNames() {
        return repository.findAll();
    }

    public void selectTree(String name) {
        if (!repository.exists(name)) {
            throw new TreeNotFoundException(name);
        }

        this.selectedTreeName = name;
    }

    public String getSelectedTreeName() {
        return selectedTreeName;
    }

    public BinarySearchTree getSelectedTree() {
        if (selectedTreeName == null) {
            return null;
        }

        return repository.findByName(selectedTreeName);
    }
}
