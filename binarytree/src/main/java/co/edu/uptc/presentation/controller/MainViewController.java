
package co.edu.uptc.presentation.controller;

import co.edu.uptc.App;
import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;
import co.edu.uptc.domain.model.TreeManager;
import co.edu.uptc.infraestructure.persistence.BinaryTreeRepository;
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

import java.io.IOException;
import java.util.Map;

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

    private Integer highlightedValue = null;

    @FXML
    public void initialize() {

        treeManager = new TreeManager();

        refreshTreeComboBox();

        logMessage(
                "Aplicación iniciada. Crea un nuevo árbol para comenzar."
        );
    }

    public void refreshTreeComboBox() {

        comboTrees.getItems().clear();

        if (treeManager != null && treeManager.getTrees() != null) {

            comboTrees.getItems().addAll(
                    treeManager.getTrees().keySet()
            );
        }

        if (!comboTrees.getItems().isEmpty()) {

            comboTrees.getSelectionModel().selectFirst();
        }

        highlightedValue = null;

        redrawSelectedTree();
    }

    @FXML
    private void onCreateTree() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    App.class.getResource(
                            "fxml/CreateTreeView.fxml"
                    )
            );

            Parent root = loader.load();

            CreateTreeController controller =
                    loader.getController();

            controller.setTreeManager(treeManager);
            controller.setMainController(this);

            Stage stage = new Stage();

            stage.setTitle("Crear nuevo árbol");

            stage.setScene(
                    new Scene(root)
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.setResizable(false);

            stage.showAndWait();

        } catch (IOException e) {

            e.printStackTrace();

            showError(
                    "Error al abrir la ventana de creación: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void onLoadTree() {

        try {

            BinaryTreeRepository repository =
                    new JsonRepository("arboles.json");

            Map<String, BinaryTree> loadedTrees =
                    repository.loadList();

            if (loadedTrees.isEmpty()) {

                showError(
                        "No hay árboles guardados en 'data/arboles.json'. "
                                + "Inserta y guarda uno primero."
                );

                return;
            }

            treeManager.setTrees(loadedTrees);

            refreshTreeComboBox();

            showSuccess(
                    "¡Árboles cargados con éxito desde el archivo JSON!"
            );

        } catch (Exception e) {

            showError(
                    "Error al cargar la persistencia: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void onSaveTree() {

        try {

            BinaryTreeRepository repository =
                    new JsonRepository("arboles.json");

            repository.saveList(
                    treeManager.getTrees()
            );

            showSuccess(
                    "¡Todos los árboles se guardaron en "
                            + "'data/arboles.json'!"
            );

        } catch (Exception e) {

            showError(
                    "Error al guardar: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void onDeleteTree() {

        String selected =
                comboTrees.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Selecciona un árbol para eliminar."
            );

            return;
        }

        Alert alert =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        alert.setTitle(
                "Confirmar eliminación"
        );

        alert.setHeaderText(
                "¿Estás seguro de eliminar el árbol '"
                        + selected + "'?"
        );

        alert.setContentText(
                "Esta acción no se puede deshacer."
        );

        if (alert.showAndWait().orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            treeManager.deleteTree(selected);

            highlightedValue = null;

            refreshTreeComboBox();

            logMessage(
                    "Árbol '" + selected
                            + "' eliminado."
            );
        }
    }

    @FXML
    private void onSelectTree() {

        String selected =
                comboTrees.getSelectionModel()
                        .getSelectedItem();

        if (selected != null) {

            highlightedValue = null;

            logMessage(
                    "Árbol seleccionado: "
                            + selected
            );

            redrawSelectedTree();
        }
    }

    @FXML
    private void onInsert() {

        String selected =
                comboTrees.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Selecciona un árbol para agregar el nodo."
            );

            return;
        }

        String input =
                valueField.getText();

        if (input == null || input.trim().isEmpty()) {

            messagesArea.setText(
                    "Por favor, ingrese un número para insertar."
            );

            return;
        }

        try {

            int valueToInsert =
                    Integer.parseInt(
                            input.trim()
                    );

            treeManager.insertValue(
                    selected,
                    valueToInsert
            );

            highlightedValue = null;

            redrawSelectedTree();

            messagesArea.setText(
                    "¡Nodo " + valueToInsert
                            + " agregado al árbol '"
                            + selected + "'!"
            );

            valueField.clear();

        } catch (NumberFormatException e) {

            messagesArea.setText(
                    "Error: Debe ingresar un número entero válido."
            );

        } catch (DuplicateValueException e) {

            messagesArea.setText(
                    "Error: El valor ya existe en el árbol."
            );

        } catch (IllegalStateException e) {

            messagesArea.setText(
                    "Error de estado: "
                            + e.getMessage()
            );

        } catch (Exception e) {

            messagesArea.setText(
                    "Error inesperado: "
                            + e.getMessage()
            );
        }
    }

    private void redrawSelectedTree() {

        String selected =
                comboTrees.getSelectionModel()
                        .getSelectedItem();

        BinaryTree tree =
                selected == null
                        ? null
                        : treeManager.getTree(selected);

        treeDrawer.draw(
                tree,
                treeDrawingPanel
        );

        if (highlightedValue != null
                && tree != null
                && !tree.isEmpty()) {

            treeDrawer.highlightNode(
                    treeDrawingPanel,
                    highlightedValue
            );
        }
    }

    @FXML
    private void onSearch() {

        String selected =
                comboTrees.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            showError(
                    "Selecciona un árbol para realizar la búsqueda."
            );

            return;
        }

        String input =
                valueField.getText();

        if (input == null || input.trim().isEmpty()) {

            messagesArea.setText(
                    "Por favor, ingrese un número para buscar."
            );

            return;
        }

        try {

            int valueToSearch =
                    Integer.parseInt(
                            input.trim()
                    );

            Node foundNode =
                    treeManager.searchNode(
                            selected,
                            valueToSearch
                    );

            highlightedValue =
                    foundNode.getValue();

            redrawSelectedTree();

            messagesArea.setText(
                    "¡Nodo "
                            + foundNode.getValue()
                            + " encontrado!"
            );

            valueField.clear();

        } catch (NumberFormatException e) {

            highlightedValue = null;

            treeDrawer.resetNodeStyles(
                    treeDrawingPanel
            );

            messagesArea.setText(
                    "Error: Debe ingresar un número entero válido."
            );

        } catch (ValueNotFoundException e) {

            highlightedValue = null;

            treeDrawer.resetNodeStyles(
                    treeDrawingPanel
            );

            messagesArea.setText(
                    "El valor no existe en el árbol."
            );

        } catch (IllegalStateException e) {

            highlightedValue = null;

            treeDrawer.resetNodeStyles(
                    treeDrawingPanel
            );

            messagesArea.setText(
                    "Error de estado: "
                            + e.getMessage()
            );

        } catch (Exception e) {

            highlightedValue = null;

            treeDrawer.resetNodeStyles(
                    treeDrawingPanel
            );

            messagesArea.setText(
                    "Error inesperado: "
                            + e.getMessage()
            );
        }
    }

    @FXML
    private void onDeleteValue() {

        logMessage(
                "Función de eliminación de valor en desarrollo..."
        );
    }

    @FXML
    private void onPreorder() {

        logMessage(
                "Recorrido preorden en desarrollo..."
        );
    }

    @FXML
    private void onInorder() {

        logMessage(
                "Recorrido inorden en desarrollo..."
        );
    }

    @FXML
    private void onPostorder() {

        logMessage(
                "Recorrido postorden en desarrollo..."
        );
    }
    private void logMessage(String message) {

        messagesArea.appendText(
                message + "\n"
        );
    }

    private void showError(String message) {

        messagesArea.appendText(
                message + "\n"
        );
    }
    private void showSuccess(String message) {

        messagesArea.appendText(
                message + "\n"
        );
    }
}
