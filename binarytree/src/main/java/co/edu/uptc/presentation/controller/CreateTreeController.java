package co.edu.uptc.presentation.controller;

import co.edu.uptc.application.service.TreeManager;
import co.edu.uptc.domain.exception.DuplicateTreeException;
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
            showError("Por favor, ingrese un nombre para el árbol.");
            return;
        }

     
        if (treeName.length() > 20) {
            showError("El nombre no puede tener más de 20 caracteres.");
            return;
        }

   
        if (!treeName.matches("[a-zA-Z0-9]+")) {
            showError("El nombre no debe contener caracteres especiales ni espacios.");
            return;
        }

        try {
            treeManager.createTree(treeName);
            messageLabel.setText(" Árbol '" + treeName + "' creado correctamente.");
            messageLabel.setStyle("-fx-text-fill: #2e8b57;");

 
            if (mainController != null) {
                mainController.refreshTreeComboBox();
            }


            treeNameField.clear();

  
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

        } catch (DuplicateTreeException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle("-fx-text-fill: #c0392b;");
    }

    @FXML
    private void onCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}