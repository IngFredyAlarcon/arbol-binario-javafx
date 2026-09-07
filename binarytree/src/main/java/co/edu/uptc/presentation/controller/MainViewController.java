package co.edu.uptc.presentation.controller;

import java.io.IOException;
import java.util.Optional;

import co.edu.uptc.App;
import co.edu.uptc.application.service.EliminarArbolService;
import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;
import co.edu.uptc.infraestructure.exception.PersistenceException;
import co.edu.uptc.infraestructure.persistence.JsonBinaryTreeRepository;
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

    private EliminarArbolService sv;
    private TreeManager treeManager;

    @FXML
    public void initialize() {
        this.sv = new EliminarArbolService(new JsonBinaryTreeRepository());
        this.treeManager = new TreeManager();
        refreshTreeComboBox();
        logMessage("Aplicación iniciada. Crea un nuevo árbol para comenzar.");
    }

    public void refreshTreeComboBox() {
        comboTrees.getItems().clear();
        
        // Cargar nombres desde el servicio
        if (sv != null) {
            comboTrees.getItems().addAll(sv.obtenerNombresArboles());
        }

        // Cargar también desde treeManager si posee elementos adicionales
        if (treeManager != null && treeManager.getTrees() != null) {
            for (BinaryTree tree : treeManager.getTrees().values()) {
                if (!comboTrees.getItems().contains(tree.getName())) {
                    comboTrees.getItems().add(tree.getName());
                }
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
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al abrir la ventana de creación: " + e.getMessage());
        }
    }

    @FXML
    private void onLoadTree() {
        logMessage("Función de carga en desarrollo...");
    }

    @FXML
    private void onSaveTree() {
        logMessage("Función de guardado en desarrollo...");
    }

    @FXML
    private void onDeleteTree() {
        String arbolSeleccionado = comboTrees.getValue();
        try {
            if (arbolSeleccionado == null || arbolSeleccionado.trim().isEmpty()) {
                throw new NoTreeSelectedException();
            }

            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText(null);
            confirmacion.setContentText("¿Está seguro de que desea eliminar el árbol '" + arbolSeleccionado + "'?");

            Optional<ButtonType> resultado = confirmacion.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                sv.eliminarArbol(arbolSeleccionado);
                
                if (treeManager != null) {
                    treeManager.deleteTree(arbolSeleccionado);
                }
                
                refreshTreeComboBox();

                if (messagesArea != null) {
                    messagesArea.appendText("Árbol '" + arbolSeleccionado + "' eliminado exitosamente.\n");
                }
            }
        } catch (NoTreeSelectedException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", e.getMessage());
        } catch (TreeNotFoundException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Árbol no encontrado", e.getMessage());
        } catch (PersistenceException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de archivo", "No se pudo actualizar el archivo: " + e.getMessage());
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error no controlado: " + e.getMessage());
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
        if (messagesArea != null) {
            messagesArea.appendText(message + "\n");
        }
    }

    private void showError(String message) {
        if (messagesArea != null) {
            messagesArea.appendText(message + "\n");
        }
    }

    private void showSuccess(String message) {
        if (messagesArea != null) {
            messagesArea.appendText(message + "\n");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}