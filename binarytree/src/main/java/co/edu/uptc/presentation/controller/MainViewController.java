package co.edu.uptc.presentation.controller;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;
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
            updateTreeDrawing(treeManager.getTree(selected));
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
            messagesArea.setText("¡Nodo "+ input+" agregado al arbol " + selected + " encontrado!");
            BinaryTree currenTree= treeManager.getTree(selected);
            updateTreeDrawing(currenTree);
            valueField.clear();

        } catch (NumberFormatException e) {
            messagesArea.setText("Error: Debe ingresar un número entero válido.");
        } catch (IllegalStateException e) {
            messagesArea.setText("Error de estado: " + e.getMessage());
        } catch (Exception e) {
            // Captura de seguridad para cualquier otro error imprevisto
            messagesArea.setText("Error inesperado: " + e.getMessage());
        }
    }

    @FXML
    private void onSearch() {
        String selected = comboTrees.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Selecciona un árbol para agregar el nodo.");
            return;
        }
        String input = valueField.getText();
        if (input == null || input.trim().isEmpty()) {
            messagesArea.setText("Por favor, ingrese un número para buscar.");
            return;
        }

        try {
            int valueToSearch = Integer.parseInt(input.trim());
            Node foundNode = treeManager.searchNode(selected,valueToSearch);
            messagesArea.setText("¡Nodo " + foundNode.getValue() + " encontrado!");
            highlightNodeInPanel(valueToSearch);

        } catch (NumberFormatException e) {
            messagesArea.setText("Error: Debe ingresar un número entero válido.");
        } catch (ValueNotFoundException e) {
            messagesArea.setText("El valor no existe en el árbol.");
            resetNodeStyles();
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
        messagesArea.appendText("❌ " + message + "\n");
    }

    private void showSuccess(String message) {
        messagesArea.appendText("✅ " + message + "\n");
    }

    /**
     * Limpia el panel y calcula el ancho real con márgenes de seguridad para evitar que los nodos se salgan.
     * @param tree El árbol binario que se va a dibujar.
     */
    private void updateTreeDrawing(BinaryTree tree) {
        treeDrawingPanel.getChildren().clear(); 

        if (tree != null && tree.getRoot() != null) {
            double width = treeDrawingPanel.getWidth();
            if (width <= 0) {
                width = treeDrawingPanel.getPrefWidth() > 0 ? treeDrawingPanel.getPrefWidth() : 800;
            }
            double padding = 40;
            double minX = padding;
            double maxX = width - padding;
            
            double startY = 50; 
            double yOffset = 60; 
            
            drawNodeRecursive(tree.getRoot(), minX, maxX, startY, yOffset);
        }
    }


    private void drawNodeRecursive(Node node, double minX, double maxX, double y, double yOffset) {
        if (node == null) return;
        double currentX = (minX + maxX) / 2;

        if (node.getLeft() != null) {
            double leftChildX = (minX + currentX) / 2; 
            
            javafx.scene.shape.Line leftLine = new javafx.scene.shape.Line(currentX, y, leftChildX, y + yOffset);
            treeDrawingPanel.getChildren().add(leftLine);
            
            drawNodeRecursive(node.getLeft(), minX, currentX, y + yOffset, yOffset);
        }


        if (node.getRight() != null) {
            double rightChildX = (currentX + maxX) / 2;
            
            javafx.scene.shape.Line rightLine = new javafx.scene.shape.Line(currentX, y, rightChildX, y + yOffset);
            treeDrawingPanel.getChildren().add(rightLine);
            
            drawNodeRecursive(node.getRight(), currentX, maxX, y + yOffset, yOffset);
        }

        javafx.scene.layout.StackPane nodeUI = createNodeUI(node.getValue(), currentX, y);
        treeDrawingPanel.getChildren().add(nodeUI);
    }

    private javafx.scene.layout.StackPane createNodeUI(int value, double x, double y) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(20);
        circle.getStyleClass().add("tree-node");

        javafx.scene.control.Label text = new javafx.scene.control.Label(String.valueOf(value));
        text.getStyleClass().add("node-text");

        javafx.scene.layout.StackPane group = new javafx.scene.layout.StackPane(circle, text);
        group.setLayoutX(x - 20);
        group.setLayoutY(y - 20);
        group.setUserData(value); 

        return group;
    }


    private void highlightNodeInPanel(int targetValue) {
        resetNodeStyles();
        for (javafx.scene.Node element : treeDrawingPanel.getChildren()) {
            if (element instanceof javafx.scene.layout.StackPane) {
                javafx.scene.layout.StackPane nodeGroup = (javafx.scene.layout.StackPane) element;
                if (nodeGroup.getUserData() != null && (int) nodeGroup.getUserData() == targetValue) {
                    for (javafx.scene.Node child : nodeGroup.getChildren()) {
                        if (child instanceof javafx.scene.shape.Circle) {
                            javafx.scene.shape.Circle circle = (javafx.scene.shape.Circle) child;
                            circle.setStyle("-fx-fill: #f1c40f; -fx-stroke: #e67e22; -fx-stroke-width: 3;");
                        }
                    }
                }
            }
        }
    }

    private void resetNodeStyles() {
        for (javafx.scene.Node element : treeDrawingPanel.getChildren()) {
            if (element instanceof javafx.scene.layout.StackPane) {
                javafx.scene.layout.StackPane nodeGroup = (javafx.scene.layout.StackPane) element;
                for (javafx.scene.Node child : nodeGroup.getChildren()) {
                    if (child instanceof javafx.scene.shape.Circle) {
                        javafx.scene.shape.Circle circle = (javafx.scene.shape.Circle) child;
                        circle.setStyle(""); 
                    }
                }
            }
        }
    }
}