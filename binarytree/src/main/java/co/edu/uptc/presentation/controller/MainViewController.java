package co.edu.uptc.presentation.controller;

import java.util.Optional;

import co.edu.uptc.application.service.EliminarArbolService;
import co.edu.uptc.infraestructure.persistence.JsonBinaryTreeRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    private EliminarArbolService sv;

    @FXML
    public void initialize() {
        this.sv = new EliminarArbolService(new JsonBinaryTreeRepository());
        comboTrees.getItems().setAll(sv.obtenerNombresArboles());
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
        String arbolSeleccionado = comboTrees.getValue();

        // 1. Valida si hay un arbol seleccionado o no
        if (arbolSeleccionado == null || arbolSeleccionado.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Por favor seleccione un árbol primero.");
            return;
        }

        // 2. Manda una alerta para confirmar o cancelar la eliminacion del arbol
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Está seguro de que desea eliminar el árbol '" + arbolSeleccionado + "'?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        // 3. Elimina el arbol en el JSON y de forma visual en el comboBox
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                sv.eliminarArbol(arbolSeleccionado);
                
                comboTrees.getItems().remove(arbolSeleccionado);
                comboTrees.setValue(null);

                if (messagesArea != null) {
                    messagesArea.appendText("Árbol '" + arbolSeleccionado + "' eliminado del archivo JSON.\n");
                }
            } catch (Exception e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el árbol: " + e.getMessage());
            }
        }
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}