package co.edu.uptc.presentation.controller;

import co.edu.uptc.application.service.TreeSelectionService;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;
import co.edu.uptc.infraestructure.persistence.InMemoryBinaryTreeRepository;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.function.BiConsumer;

public class TreeSelectionController {

    private TreeSelectionService treeSelectionService =
            new TreeSelectionService(new InMemoryBinaryTreeRepository());

    private Stage dialogStage;
    private BiConsumer<String, BinarySearchTree> onTreeLoaded;

    @FXML
    private ComboBox<String> treeSelector;

    @FXML
    private Button selectButton;

    @FXML
    private Label selectedTreeInfoLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private void initialize() {
        refreshAvailableTrees();
    }

    /**
     * Permite reutilizar este control con un repositorio compartido con el resto
     * de la aplicación (por ejemplo, el mismo que usa la ventana principal), en
     * vez del repositorio de prueba creado por defecto.
     */
    public void setRepository(BinaryTreeRepository repository) {
        this.treeSelectionService = new TreeSelectionService(repository);
        refreshAvailableTrees();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /** Se invoca cuando el usuario carga un árbol correctamente: nombre y árbol cargado. */
    public void setOnTreeLoaded(BiConsumer<String, BinarySearchTree> onTreeLoaded) {
        this.onTreeLoaded = onTreeLoaded;
    }

    private void refreshAvailableTrees() {
        boolean hasTrees = !treeSelectionService.getAvailableTreeNames().isEmpty();

        treeSelector.setItems(FXCollections.observableArrayList(treeSelectionService.getAvailableTreeNames()));
        treeSelector.setDisable(!hasTrees);
        selectButton.setDisable(!hasTrees);

        feedbackLabel.setText(hasTrees ? "" : "No hay árboles guardados todavía.");
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
            feedbackLabel.setText("Árbol '" + name + "' cargado correctamente.");

            if (onTreeLoaded != null) {
                onTreeLoaded.accept(name, treeSelectionService.getSelectedTree());
            }

            if (dialogStage != null) {
                dialogStage.close();
            }
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
