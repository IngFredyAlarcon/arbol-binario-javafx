package co.edu.uptc.presentation.controller;


import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;
import co.edu.uptc.Export.PdfTreeExporter;
import co.edu.uptc.presentation.TreeDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class TreeDrawController {

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
    private Button btnExportPdf;
    @FXML
    private Pane treeDrawingPanel;
    @FXML
    private TextArea messagesArea;

    private TreeManager treeManager;
    private final TreeDrawer treeDrawer = new TreeDrawer();
    private final PdfTreeExporter pdfExporter = new PdfTreeExporter();

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
        if (!comboTrees.getItems().isEmpty()) {
            comboTrees.getSelectionModel().selectFirst();
        }
        redrawSelectedTree();
    }
    @FXML
    private void onExportPdf() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;

        if (tree.isEmpty()) {
            showError("No se puede exportar un árbol vacío.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar árbol a PDF");
        fileChooser.setInitialFileName(tree.getName() + ".pdf");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );

        Window ventana = btnExportPdf.getScene().getWindow();
        File archivo = fileChooser.showSaveDialog(ventana);

        if (archivo == null) {
            return;
        }

        try {
            pdfExporter.export(treeDrawingPanel, tree.getName(), archivo);
            showSuccess("Árbol exportado correctamente a " + archivo.getName());
        } catch (IOException e) {
            showError("Error al exportar el PDF: " + e.getMessage());
        }
    }

    // ---------- Métodos auxiliares ----------

    private BinaryTree getArbolSeleccionado() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona o crea un árbol primero.");
            return null;
        }
        return treeManager.getTree(selected);
    }

    private Integer leerValorEntero() {
        String texto = valueField.getText().trim();
        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            showError("Ingresa un valor entero válido.");
            return null;
        }
    }

    private void redrawSelectedTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        BinaryTree tree = selected == null ? null : treeManager.getTree(selected);
        treeDrawer.draw(tree, treeDrawingPanel);
    }

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


