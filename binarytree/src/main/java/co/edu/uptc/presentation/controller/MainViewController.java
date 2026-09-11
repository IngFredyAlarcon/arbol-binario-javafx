package co.edu.uptc.presentation.controller;

import java.io.IOException;
import java.util.Optional;

import co.edu.uptc.App;
import co.edu.uptc.application.service.TreeManager;
import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.exception.TreeNotFoundException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;
import co.edu.uptc.infraestructure.exception.PersistenceException;
import co.edu.uptc.infraestructure.persistence.JsonRepository;
import co.edu.uptc.presentation.TreeDrawer;
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

    
    private TreeManager treeManager;

    private final TreeDrawer treeDrawer = new TreeDrawer();

    @FXML
    public void initialize() {
        treeManager = new TreeManager(new JsonRepository("BinaryTree.json"));
        refreshTreeComboBox();
        logMessage("Aplicación iniciada. Crea un nuevo árbol para comenzar.");
    }

    public void refreshTreeComboBox() {
        comboTrees.getItems().clear();
        
        if(treeManager != null){
            comboTrees.getItems().addAll(treeManager.obtenerNombresArboles());
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
            redrawSelectedTree();
        } else {
            treeDrawer.draw(null, treeDrawingPanel);
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
        try {
            treeManager.loadFromRepository();
            refreshTreeComboBox();
            showSuccess("¡Árboles cargados con éxito desde el archivo JSON!");

        } catch (Exception e) {
            showError("Error al cargar la persistencia: " + e.getMessage());
        }
    }

    @FXML
    private void onSaveTree() {
        try {
            treeManager.saveTree(comboTrees.getValue(), treeManager.getTree(comboTrees.getValue()));
            showSuccess("¡Todos los árboles se guardaron exitosamente!");
        } catch (Exception e) {
            showError("Error al guardar: " + e.getMessage());
        }
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
                treeManager.deleteTree(arbolSeleccionado);
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
     private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void onSelectTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected != null) {
            logMessage("Árbol seleccionado: " + selected);
            redrawSelectedTree();
        }
    }

    @FXML 
    private void onInsert() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona un árbol para agregar el nodo.");
            return;
        }
        String input = valueField.getText();
        if (input == null || input.trim().isEmpty()) {
            messagesArea.setText("Por favor, ingrese un número para ingresar.");
            return;
        }
        try {
            int valueToSearch = Integer.parseInt(input.trim());
            treeManager.insertValue(selected, valueToSearch);
            messagesArea.setText("¡Nodo " + input + " agregado al arbol " + selected + " encontrado!");
            redrawSelectedTree();
            valueField.clear();

        } catch (NumberFormatException e) {
            messagesArea.setText("Error: Debe ingresar un número entero válido.");
        } catch (IllegalStateException e) {
            messagesArea.setText("Error de estado: " + e.getMessage());
        } catch (Exception e) {
            messagesArea.setText("Error inesperado: " + e.getMessage());
        }
    }

    private void redrawSelectedTree() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        BinaryTree tree = selected == null ? null : treeManager.getTree(selected);
        treeDrawer.draw(tree, treeDrawingPanel);
    }

    @FXML
    private void onSearch() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona un árbol para buscar el nodo.");
            return;
        }
        String input = valueField.getText();
        if (input == null || input.trim().isEmpty()) {
            messagesArea.setText("Por favor, ingrese un número para buscar.");
            return;
        }

        try {
            int valueToSearch = Integer.parseInt(input.trim());
            Node foundNode = treeManager.searchNode(selected, valueToSearch);
            messagesArea.setText("¡Nodo " + foundNode.getValue() + " encontrado!");
            treeDrawer.highlightNode(treeDrawingPanel, valueToSearch);

        } catch (NumberFormatException e) {
            messagesArea.setText("Error: Debe ingresar un número entero válido.");
            treeDrawer.resetNodeStyles(treeDrawingPanel);
        } catch (ValueNotFoundException e) {
            messagesArea.setText("El valor no existe en el árbol.");
            treeDrawer.resetNodeStyles(treeDrawingPanel);
        } catch (IllegalStateException e) {
            messagesArea.setText("Error de estado: " + e.getMessage());
        } catch (Exception e) {
            messagesArea.setText("Error inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void onDeleteValue() {
        logMessage("Función de eliminación de valor en desarrollo...");
    }

    @FXML
    private void onPreorder() {
        BinaryTree selectedTree = getSelectedTree();
        if (selectedTree == null) return;

        logMessage("Preorden: " + formatTraversal(selectedTree.getPreOrder()));
    }

    @FXML
    private void onInorder() {
        BinaryTree selectedTree = getSelectedTree();
        if (selectedTree == null) return;

        logMessage("Inorden: " + formatTraversal(selectedTree.getInOrder()));
    }

    @FXML
    private void onPostorder() {
        BinaryTree selectedTree = getSelectedTree();
        if (selectedTree == null) return;

        logMessage("Postorden: " + formatTraversal(selectedTree.getPostOrder()));
    }

    private BinaryTree getSelectedTree() {
        String selectedName = comboTrees.getSelectionModel().getSelectedItem();
        if (selectedName == null) {
            showError("No hay ningún árbol seleccionado.");
            return null;
        }
        return treeManager.getTree(selectedName);
    }

    private String formatTraversal(java.util.List list) {
        if (list == null || list.isEmpty()) {
            return "El árbol está vacío.";
        }
        return (String) list.stream()
                .map(Object::toString)
                .collect(java.util.stream.Collectors.joining(" -> "));
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