package co.edu.uptc.presentation.controller;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {

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
    private Pane treeDrawingPanel;

    @FXML
    private TextArea messagesArea;

    private TreeManager treeManager;

    @FXML
    public void initialize() {
        treeManager = new TreeManager();
        refreshTreeComboBox();
        logMessage("Aplicación iniciada. Crea un nuevo árbol para comenzar.");
    }


    public void refreshTreeComboBox() {
        comboTrees.getItems().clear();
        if (treeManager != null && treeManager.getTrees() != null) {
            // Se agregan los objetos BinaryTree directamente o sus nombres guardados
            for (BinaryTree tree : treeManager.getTrees().values()) {
                comboTrees.getItems().add(tree.getName());
            }
        }

        if (!comboTrees.getItems().isEmpty()) {
            comboTrees.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onCreateTree() {
        try {

            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/CreateTreeView.fxml"));
            Parent root = loader.load();


            CreateTreeController controller = loader.getController();
            controller.setTreeManager(treeManager);
            controller.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Crear nuevo árbol");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al abrir la ventana de creación: " + e.getMessage());
        }
    }

    @FXML
    private void onLoadTree() {

        
    }

    @FXML
    private void onSaveTree() {

        
    }

    @FXML
    private void onDeleteTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona un árbol para eliminar.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de eliminar el árbol '" + selected + "'?");
        alert.setContentText("Esta acción no se puede deshacer.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            treeManager.deleteTree(selected);
            refreshTreeComboBox();
            logMessage("Árbol '" + selected + "' eliminado.");
        }
    }

    @FXML
    private void onSelectTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected != null) {
            logMessage("Árbol seleccionado: " + selected);

        }
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

    @FXML
    private void onPreorder() {

       
    }

    @FXML
    private void onInorder() {

        
    }

    @FXML
    private void onPostorder() {

        
    }


    private void logMessage(String message) {
        messagesArea.appendText(message + "\n");
    }

    private void showError(String message) {
        messagesArea.appendText(message + "\n");
    }

    private void showSuccess(String message) {
        messagesArea.appendText(message + "\n");
    }
}