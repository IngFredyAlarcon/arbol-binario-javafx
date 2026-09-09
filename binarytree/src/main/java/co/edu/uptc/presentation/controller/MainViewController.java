package co.edu.uptc.presentation.controller;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateTreeException;
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
            
        }
    }

    @FXML
    private void onInsert() {
       
        logMessage("Función de inserción en desarrollo...");
    }

    @FXML
    private void onSearch() {
        
        logMessage("Función de búsqueda en desarrollo...");
    }

    @FXML
    private void onDeleteValue() {
        
        logMessage("Función de eliminación de valor en desarrollo...");
    }

    @FXML
    private void onPreorder() {
        
        logMessage("Recorrido preorden en desarrollo...");
    }

    @FXML
    private void onInorder() {
       
        logMessage("Recorrido inorden en desarrollo...");
    }

    @FXML
    private void onPostorder() {
        
        logMessage("Recorrido postorden en desarrollo...");
    }

  
    private void logMessage(String message) {
        messagesArea.appendText(message + "\n");
    }

    private void showError(String message) {
        messagesArea.appendText( message + "\n");
    }

    private void showSuccess(String message) {
        messagesArea.appendText( message + "\n");
    }
}