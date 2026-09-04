package co.edu.uptc.presentation.controller;
import co.edu.uptc.domain.model.BinarySearchTree;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public class DeleteNodeController {

    @FXML
    private Spinner<Integer> spinnerNode;

    private BinarySearchTree tree;

    @FXML
    public void initialize() {
        SpinnerValueFactory<Integer> valueFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 0);
        spinnerNode.setValueFactory(valueFactory);
        TextFormatter<Integer> formatter = new TextFormatter<>(
            valueFactory.getConverter(),
            valueFactory.getValue(),
            change -> change.getControlNewText().matches("\\d*") ? change : null
        );
        spinnerNode.getEditor().setTextFormatter(formatter);
    }

    public void setTree(BinarySearchTree tree) {
        this.tree = tree;
    }

    @FXML
    private void deleteNode() {

        int value = spinnerNode.getValue();

        if (tree != null) {
            tree.delete(value);
        }
    }
}