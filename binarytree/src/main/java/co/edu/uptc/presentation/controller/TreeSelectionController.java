package co.edu.uptc.presentation.controller;

import co.edu.uptc.application.service.TreeSelectionService;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.infraestructure.persistence.InMemoryBinaryTreeRepository;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class TreeSelectionController {

    private final TreeSelectionService treeSelectionService =
            new TreeSelectionService(new InMemoryBinaryTreeRepository());

    @FXML
    private ComboBox<String> treeSelector;

    @FXML
    private Label selectedTreeInfoLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private void initialize() {
        refreshAvailableTrees();
    }

    private void refreshAvailableTrees() {
        treeSelector.setItems(FXCollections.observableArrayList(treeSelectionService.getAvailableTreeNames()));

        if (treeSelectionService.getAvailableTreeNames().isEmpty()) {
            feedbackLabel.setText("No hay árboles disponibles para seleccionar.");
        }
    }

    @FXML
    private void selectTree() {
        String name = treeSelector.getValue();

        if (name == null) {
            feedbackLabel.setText("Debe elegir un árbol de la lista.");
            return;
        }

        try {
            treeSelectionService.selectTree(name);
            showSelectedTreeInfo();
            feedbackLabel.setText("Árbol '" + name + "' seleccionado correctamente.");
        } catch (TreeNotFoundException e) {
            feedbackLabel.setText(e.getMessage());
        }
    }

    private void showSelectedTreeInfo() {
        BinarySearchTree selectedTree = treeSelectionService.getSelectedTree();
        String status = selectedTree.isEmpty() ? "vacío" : selectedTree.size() + " nodo(s)";
        selectedTreeInfoLabel.setText(
                "Árbol actual: " + treeSelectionService.getSelectedTreeName() + " (" + status + ")");
    }
}
