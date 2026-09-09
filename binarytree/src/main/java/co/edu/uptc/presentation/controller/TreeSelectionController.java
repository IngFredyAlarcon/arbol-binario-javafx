package co.edu.uptc.presentation.controller;

import co.edu.uptc.application.service.TreeSelectionService;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;
import co.edu.uptc.domain.model.TreeManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class TreeSelectionController {

    private TreeSelectionService treeSelectionService;

    private Stage dialogStage;
    private BiConsumer<String, BinaryTree> onTreeSelected;

    @FXML
    private ComboBox<String> treeSelector;

    @FXML
    private Button selectButton;

    @FXML
    private Label selectedTreeInfoLabel;

    @FXML
    private Label feedbackLabel;

    /**
     * El controlador que abre este diálogo (MainViewController) debe invocar
     * este método con el TreeManager de la aplicación antes de mostrar la
     * ventana, para que el selector liste los árboles realmente activos.
     */
    public void setTreeManager(TreeManager treeManager) {
        this.treeSelectionService = new TreeSelectionService(treeManager);
        refreshAvailableTrees();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /** Se invoca cuando el usuario selecciona un árbol: nombre y árbol elegido. */
    public void setOnTreeSelected(BiConsumer<String, BinaryTree> onTreeSelected) {
        this.onTreeSelected = onTreeSelected;
    }

    private void refreshAvailableTrees() {
        boolean hasTrees = !treeSelectionService.getAvailableTreeNames().isEmpty();

        treeSelector.setItems(FXCollections.observableArrayList(treeSelectionService.getAvailableTreeNames()));
        treeSelector.setDisable(!hasTrees);
        selectButton.setDisable(!hasTrees);

        feedbackLabel.setText(hasTrees ? "" : "No hay árboles disponibles todavía.");
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

            if (onTreeSelected != null) {
                onTreeSelected.accept(name, treeSelectionService.getSelectedTree());
            }

            if (dialogStage != null) {
                dialogStage.close();
            }
        } catch (TreeNotFoundException e) {
            feedbackLabel.setText(e.getMessage());
        }
    }

    private void showSelectedTreeInfo() {
        BinaryTree selectedTree = treeSelectionService.getSelectedTree();
        String status = selectedTree.isEmpty() ? "vacío" : countNodes(selectedTree.getRoot()) + " nodo(s)";
        selectedTreeInfoLabel.setText(
                "Árbol actual: " + treeSelectionService.getSelectedTreeName() + " (" + status + ")");
    }

    private int countNodes(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
    }
}
