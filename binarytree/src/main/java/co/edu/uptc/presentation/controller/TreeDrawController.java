package co.edu.uptc.presentation.controller;


import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.TreeManager;

import co.edu.uptc.Export.PdfTreeExporter;
import co.edu.uptc.presentation.TreeDrawer;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

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
        /**Prueba de arbol creado */
    treeManager = new TreeManager();
    try {
        treeManager.createTree("prueba");
        BinaryTree arbol = treeManager.getTree("prueba");
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        for (int v : valores) {
            arbol.insert(v);
        }
    } catch (DuplicateTreeException e) {
        e.printStackTrace();
    }        
        refreshTreeComboBox();
        logMessage("Aplicación iniciada. Crea un nuevo árbol para comenzar.");
    }

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

    /**Metodo de requerimiento funcional 10 */
    @FXML
    private void onLoadTree() {
        logMessage("Función de carga en desarrollo...");
    }

    /**Metodo de requerimiento funcional 09 */
    @FXML
    private void onSaveTree() {
        
        logMessage("Función de guardado en desarrollo...");
    }
    /**Metodo para de eleminar , parte de otro requerimiento */

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

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            treeManager.deleteTree(selected);
            refreshTreeComboBox();
            logMessage("Árbol '" + selected + "' eliminado.");
        }
    }
     /**Metodo para de seleccionar , parte de otro requerimiento */

    @FXML
    private void onSelectTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected != null) {
            logMessage("Árbol seleccionado: " + selected);
        }
        redrawSelectedTree();
    }

     /**Metodo para de insertar , parte de otro requerimiento */
    @FXML
    private void onInsert() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;

        Integer valor = leerValorEntero();
        if (valor == null) return;

        try {
            tree.insert(valor);
            showSuccess("El valor " + valor + " fue insertado correctamente.");
            valueField.clear();
            redrawSelectedTree();
        } catch (DuplicateValueException e) {
            showError(e.getMessage());
        }
    }

     /**Metodo para de buscar , parte de otro requerimiento */
    @FXML
    private void onSearch() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;

        Integer valor = leerValorEntero();
        if (valor == null) return;

        if (tree.contains(valor)) {
            showSuccess("El valor " + valor + " se encuentra en el árbol.");
        } else {
            showError("El valor " + valor + " no se encuentra en el árbol.");
        }
    }

     /**Metodo para de eleminar , parte de otro requerimiento */
    @FXML
    private void onDeleteValue() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;

        Integer valor = leerValorEntero();
        if (valor == null) return;

        try {
            tree.delete(valor);
            showSuccess("El valor " + valor + " fue eliminado correctamente.");
            valueField.clear();
            redrawSelectedTree();
        } catch (ValueNotFoundException e) {
            showError(e.getMessage());
        }
    }

     /**Metodo para de recorridos , parte de otro requerimiento */
    @FXML
    private void onPreorder() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;
        List<Integer> resultado = tree.preOrder();
        logMessage("Preorden: " + resultado);
    }

    @FXML
    private void onInorder() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;
        List<Integer> resultado = tree.inOrder();
        logMessage("Inorden: " + resultado);
    }

    @FXML
    private void onPostorder() {
        BinaryTree tree = getArbolSeleccionado();
        if (tree == null) return;
        List<Integer> resultado = tree.postOrder();
        logMessage("Postorden: " + resultado);
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


