package co.edu.uptc.presentation.controller;

import co.edu.uptc.domain.model.BinarySearchTree;
import co.edu.uptc.domain.repository.BinaryTreeRepository;
import co.edu.uptc.infraestructure.persistence.InMemoryBinaryTreeRepository;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

    private final BinaryTreeRepository repository = new InMemoryBinaryTreeRepository();

    private BinarySearchTree currentTree;
    private String currentTreeName;

    // ===== Top bar =====
    @FXML
    private ComboBox<String> comboTrees;
    @FXML
    private Button btnNewTree;
    @FXML
    private Button btnLoadTree;
    @FXML
    private Button btnSaveTree;
    @FXML
    private Button btnDeleteTree;
    // ===== Left panel (operations) =====
    @FXML
    private TextField valueField;
    @FXML
    private Button btnInsert;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnDeleteValue;
    @FXML
    private Button btnPreorder;
    @FXML
    private Button btnInorder;
    @FXML
    private Button btnPostorder;
    @FXML
    private VBox extraOperationsPanel;

    // ===== Center =====
    @FXML
    private Pane treeDrawingPanel;

    // ===== Right =====
    @FXML
    private VBox infoPanel;

    // ===== Bottom =====
    @FXML
    private TextArea messagesArea;

    @FXML
    public void initialize() {
        seedSampleTrees();
        refreshTreeCombo();
    }

    /**
     * Carga árboles de ejemplo en el repositorio para poder probar la carga
     * de árboles (RF-11) mientras no exista la creación/guardado de árboles
     * desde la interfaz (RF pendientes).
     */
    private void seedSampleTrees() {
        BinarySearchTree balanced = new BinarySearchTree();
        for (int value : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            balanced.insert(value);
        }
        repository.save("Árbol balanceado", balanced);

        BinarySearchTree ascending = new BinarySearchTree();
        for (int value : new int[] {10, 20, 30, 40, 50}) {
            ascending.insert(value);
        }
        repository.save("Árbol ascendente", ascending);

        repository.save("Árbol vacío", new BinarySearchTree());
    }

    private void refreshTreeCombo() {
        comboTrees.setItems(FXCollections.observableArrayList(repository.findAll()));
    }

    private void logMessage(String message) {
        messagesArea.appendText(message + System.lineSeparator());
    }

    // ===== Tree management (CRUD) =====

    @FXML
    private void onSelectTree() {
        String name = comboTrees.getValue();

        if (name == null) {
            return;
        }

        currentTreeName = name;
        currentTree = repository.findByName(name);
        logMessage("Árbol activo: " + name + ".");
    }

    @FXML
    private void onCreateTree() {

    }

    @FXML
    private void onLoadTree() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uptc/fxml/tree-selection.fxml"));
            Parent root = loader.load();

            TreeSelectionController controller = loader.getController();
            controller.setRepository(repository);
            controller.setOnTreeLoaded(this::handleTreeLoaded);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cargar árbol");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setScene(new Scene(root));
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (IOException e) {
            logMessage("No fue posible abrir la ventana de carga: " + e.getMessage());
        }
    }

    private void handleTreeLoaded(String name, BinarySearchTree tree) {
        currentTreeName = name;
        currentTree = tree;

        refreshTreeCombo();
        comboTrees.setValue(name);
        logMessage("Árbol '" + name + "' cargado correctamente.");
    }

    @FXML
    private void onSaveTree() {

    }

    @FXML
    private void onDeleteTree() {

    }

    @FXML
    private void onInsert() {

    }

    @FXML
    private void onSearch() {

    }

    @FXML
    private void onDeleteValue() {

    }

    // ===== Traversals =====

    @FXML
    private void onPreorder() {
    }

    @FXML
    private void onInorder() {

    }

    @FXML
    private void onPostorder() {

    }
}