package co.edu.uptc.presentation.controller;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    // Método para refrescar el ComboBox con los nombres de los árboles
    public void refreshTreeComboBox() {
        comboTrees.getItems().clear();
        if (treeManager != null && treeManager.getTrees() != null) {
            comboTrees.getItems().addAll(treeManager.getTrees().keySet());
        }
        // Seleccionar el primer elemento si hay alguno
        if (!comboTrees.getItems().isEmpty()) {
            comboTrees.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onCreateTree() {
        try {
            // Cargar la vista de creación de árbol
            FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/CreateTreeView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador y pasarle el TreeManager y este controlador
            CreateTreeController controller = loader.getController();
            controller.setTreeManager(treeManager);
            controller.setMainController(this);

            // Crear una nueva ventana (Stage)
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
        // TODO: Implementar carga de árbol desde archivo
        logMessage("Función de carga en desarrollo...");
    }

    @FXML
    private void onSaveTree() {
        // TODO: Implementar guardado de árbol
        logMessage("Función de guardado en desarrollo...");
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
            // Aquí podrías cargar el árbol seleccionado para mostrarlo
        }
    }

    @FXML
    private void onInsert() {
        // TODO: Implementar inserción
        logMessage("Función de inserción en desarrollo...");
    }

    @FXML
    private void onSearch() {
        // TODO: Implementar búsqueda
        logMessage("Función de búsqueda en desarrollo...");
    }

    @FXML
    private void onDeleteValue() {
        // TODO: Implementar eliminación de valor
        logMessage("Función de eliminación de valor en desarrollo...");
    }

    @FXML
    private void onPreorder() {
        // TODO: Implementar recorrido preorden
        logMessage("Recorrido preorden en desarrollo...");
    }

    @FXML
    private void onInorder() {
        // TODO: Implementar recorrido inorden
        logMessage("Recorrido inorden en desarrollo...");
    }

    @FXML
    private void onPostorder() {
        // TODO: Implementar recorrido postorden
        logMessage("Recorrido postorden en desarrollo...");
    }

    // Métodos auxiliares
    private void logMessage(String message) {
        messagesArea.appendText(message + "\n");
    }

    private void showError(String message) {
        messagesArea.appendText("❌ " + message + "\n");
    }

    private void showSuccess(String message) {
        messagesArea.appendText("✅ " + message + "\n");
    }
}