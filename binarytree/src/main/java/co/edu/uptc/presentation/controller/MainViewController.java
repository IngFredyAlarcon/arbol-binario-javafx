package co.edu.uptc.presentation.controller;

import java.io.IOException;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinarySearchTree;
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
import javafx.stage.Stage;
;

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

    private BinarySearchTree tree = new BinarySearchTree();

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

    }

    @FXML
    private void onSearch() {

    }

    @FXML
    private void onDeleteValue() {
        try {
            int value = Integer.parseInt(valueField.getText().trim());
            tree.delete(value);
            messagesArea.appendText("Nodo " + value + " eliminado.\n");
            valueField.clear();
        } catch (NumberFormatException e) {
            messagesArea.appendText("Digite un valor entero válido.\n");
        } catch (ValueNotFoundException e) {
            messagesArea.appendText(e.getMessage() + "\n");
        }
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