package co.edu.uptc.presentation.controller;

import co.edu.uptc.domain.exception.DuplicateTreeException;
import co.edu.uptc.domain.exception.TreeNameTooLongException;
import co.edu.uptc.domain.model.TreeManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateTreeController {

    @FXML
    private TextField treeNameField;

    @FXML
    private Button btnCreate;

    @FXML
    private Button btnCancel;

    @FXML
    private Label messageLabel;

    private TreeManager treeManager;
    private MainViewController mainController;

    public void setTreeManager(TreeManager treeManager) {
        this.treeManager = treeManager;
    }

    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onCreateTree() {
        String treeName = treeNameField.getText().trim();

        if (treeName.isEmpty()) {
            messageLabel.setText("❌ Por favor, ingrese un nombre para el árbol.");
            messageLabel.setStyle("-fx-text-fill: #c0392b;");
            return;
        }

        try {
            treeManager.createTree(treeName);
            messageLabel.setText("✅ Árbol '" + treeName + "' creado correctamente.");
            messageLabel.setStyle("-fx-text-fill: #2e8b57;");

            // Actualizar el combo en la vista principal
            if (mainController != null) {
                mainController.refreshTreeComboBox();
            }

            // Limpiar el campo
            treeNameField.clear();

            // Cerrar la ventana después de 1.5 segundos
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    javafx.application.Platform.runLater(() -> {
                        Stage stage = (Stage) btnCreate.getScene().getWindow();
                        stage.close();
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (TreeNameTooLongException e) {
            // Mostrar mensaje de error por nombre demasiado largo
            messageLabel.setText("❌ " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #c0392b;");
            
            // Opcional: resaltar el campo de texto para indicar el error
            treeNameField.setStyle("-fx-border-color: #c0392b; -fx-border-width: 2px;");
            
        } catch (DuplicateTreeException e) {
            messageLabel.setText("❌ " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: #c0392b;");
            
            // Limpiar el resaltado del campo si estaba resaltado
            treeNameField.setStyle(null);
        }
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}