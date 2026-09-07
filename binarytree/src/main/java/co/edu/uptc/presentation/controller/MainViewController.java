package co.edu.uptc.presentation.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainViewController {

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
    }

    // ===== Tree management (CRUD) =====

    @FXML
    private void onSelectTree() {

    }

    @FXML
    private void onCreateTree() {

    }

    @FXML
    private void onLoadTree() {

    }

    @FXML
    private void onSaveTree() {

    }

    @FXML
    private void onDeleteTree() {

    }

    
    @FXML
    private void onInsert() {
        
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona un árbol antes de insertar.");
            return;
        }

        
        String input = valueField.getText().trim();
        if (input.isEmpty()) {
            showError("Ingrese un valor antes de insertar.");
            return;
        }

        
        try {
            int value = Integer.parseInt(input);
            BinaryTree tree = treeManager.getTree(selected);
            tree.insert(value);
            showSuccess("El valor " + value + " fue insertado correctamente.");
            valueField.clear();
        } catch (NumberFormatException e) {
            showError("Solo se permiten valores enteros.");
        } catch (DuplicateValueException e) {
            showError(e.getMessage());
        }
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