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
            
            // Llama al servicio que inserta el nuevo
            treeManager.insertValue(selected, valueToSearch);
            // Muestra el mensaje de éxito
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
            
            // Llama al servicio
            Node foundNode = treeManager.searchNode(selected,valueToSearch);
            
            // Muestra el mensaje de éxito
            messagesArea.setText("¡Nodo " + foundNode.getValue() + " encontrado!");
            
            // Resalta el nodo en pantalla
            highlightNodeInPanel(valueToSearch);

        } catch (NumberFormatException e) {
            messagesArea.setText("Error: Debe ingresar un número entero válido.");
        } catch (ValueNotFoundException e) {
            messagesArea.setText("El valor no existe en el árbol.");
            resetNodeStyles();
        } catch (IllegalStateException e) {
            // AQUÍ ATRAPAMOS EL ERROR QUE TE SALIÓ EN CONSOLA
            messagesArea.setText("Error de estado: " + e.getMessage());
        } catch (Exception e) {
            // Captura de seguridad para cualquier otro error imprevisto
            messagesArea.setText("Error inesperado: " + e.getMessage());
        }
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

    /**
     * Limpia el panel y calcula el ancho real con márgenes de seguridad para evitar que los nodos se salgan.
     * @param tree El árbol binario que se va a dibujar.
     */
    private void updateTreeDrawing(BinaryTree tree) {
        treeDrawingPanel.getChildren().clear(); 

        if (tree != null && tree.getRoot() != null) {
            // Obtener el ancho dinámico del panel o usar el valor preferido como respaldo
            double width = treeDrawingPanel.getWidth();
            if (width <= 0) {
                width = treeDrawingPanel.getPrefWidth() > 0 ? treeDrawingPanel.getPrefWidth() : 800;
            }

            // Margen de seguridad (40px) a los lados para que el radio del círculo y textos largos no se salgan
            double padding = 40;
            double minX = padding;
            double maxX = width - padding;
            
            double startY = 50;  // Margen superior
            double yOffset = 60; // Distancia vertical entre niveles
            
            drawNodeRecursive(tree.getRoot(), minX, maxX, startY, yOffset);
        }
    }

    /**
     * Dibuja el árbol basándose en la división del espacio disponible (minX a maxX).
     */
    private void drawNodeRecursive(Node node, double minX, double maxX, double y, double yOffset) {
        if (node == null) return;

        // La posición X del nodo actual es exactamente el centro de su espacio asignado
        double currentX = (minX + maxX) / 2;

        // 1. Dibujar línea e hijo izquierdo
        if (node.getLeft() != null) {
            // El espacio del hijo izquierdo va desde el inicio de este sector (minX) hasta la posición del padre (currentX)
            double leftChildX = (minX + currentX) / 2; 
            
            javafx.scene.shape.Line leftLine = new javafx.scene.shape.Line(currentX, y, leftChildX, y + yOffset);
            treeDrawingPanel.getChildren().add(leftLine);
            
            drawNodeRecursive(node.getLeft(), minX, currentX, y + yOffset, yOffset);
        }

        // 2. Dibujar línea e hijo derecho
        if (node.getRight() != null) {
            // El espacio del hijo derecho va desde la posición del padre (currentX) hasta el final de este sector (maxX)
            double rightChildX = (currentX + maxX) / 2;
            
            javafx.scene.shape.Line rightLine = new javafx.scene.shape.Line(currentX, y, rightChildX, y + yOffset);
            treeDrawingPanel.getChildren().add(rightLine);
            
            drawNodeRecursive(node.getRight(), currentX, maxX, y + yOffset, yOffset);
        }

        // 3. Dibujar el nodo actual sobre las líneas
        javafx.scene.layout.StackPane nodeUI = createNodeUI(node.getValue(), currentX, y);
        treeDrawingPanel.getChildren().add(nodeUI);
    }
    /**
     * Crea el componente gráfico individual con sintaxis clásica de Java.
     */
    private javafx.scene.layout.StackPane createNodeUI(int value, double x, double y) {
        javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(20);
        circle.getStyleClass().add("tree-node");

        javafx.scene.control.Label text = new javafx.scene.control.Label(String.valueOf(value));
        text.getStyleClass().add("node-text");

        javafx.scene.layout.StackPane group = new javafx.scene.layout.StackPane(circle, text);
        // Centramos el StackPane usando el radio del círculo (20)
        group.setLayoutX(x - 20);
        group.setLayoutY(y - 20);
        group.setUserData(value); // Guardamos el valor para que la búsqueda funcione luego

        return group;
    }

    /**
     * Busca el nodo visual en el panel y lo resalta.
     */
    private void highlightNodeInPanel(int targetValue) {
        // 1. Limpiamos cualquier resaltado anterior
        resetNodeStyles();

        // 2. Recorremos los dibujos del panel
        for (javafx.scene.Node element : treeDrawingPanel.getChildren()) {
            
            // 3. Filtramos los contenedores de nodos (StackPane)
            if (element instanceof javafx.scene.layout.StackPane) {
                javafx.scene.layout.StackPane nodeGroup = (javafx.scene.layout.StackPane) element;
                
                // 4. Comparamos el "post-it" (UserData) con el valor buscado
                if (nodeGroup.getUserData() != null && (int) nodeGroup.getUserData() == targetValue) {
                    
                    // 5. Encontramos el correcto, buscamos su círculo y lo pintamos
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

    /**
     * Limpia el estilo de todos los círculos para devolverlos a la normalidad.
     */
    private void resetNodeStyles() {
        for (javafx.scene.Node element : treeDrawingPanel.getChildren()) {
            if (element instanceof javafx.scene.layout.StackPane) {
                javafx.scene.layout.StackPane nodeGroup = (javafx.scene.layout.StackPane) element;
                for (javafx.scene.Node child : nodeGroup.getChildren()) {
                    if (child instanceof javafx.scene.shape.Circle) {
                        javafx.scene.shape.Circle circle = (javafx.scene.shape.Circle) child;
                        circle.setStyle(""); // Borra el CSS inyectado
                    }
                }
            }
        }
    }
}