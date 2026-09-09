package co.edu.uptc.application.service;

import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;

import java.util.ArrayList;
import java.util.List;

public class TreeSelectionService {

    private final TreeManager treeManager;
    private String selectedTreeName;

    public TreeSelectionService(TreeManager treeManager) {
        this.treeManager = treeManager;
    }

    public List<String> getAvailableTreeNames() {
        return new ArrayList<>(treeManager.getTrees().keySet());
    }

    public void selectTree(String name) {
        if (!treeManager.treeExists(name)) {
            throw new TreeNotFoundException(name);
        }

        this.selectedTreeName = name;
    }

    public String getSelectedTreeName() {
        return selectedTreeName;
    }

    public BinaryTree getSelectedTree() {
        if (selectedTreeName == null) {
            return null;
        }

        return treeManager.getTree(selectedTreeName);
    }
}
