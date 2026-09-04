package co.edu.uptc.presentation.controller;

import java.util.Optional;

import co.edu.uptc.application.service.TreeService;
import co.edu.uptc.domain.exception.NoTreeSelectedException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

public class LimpiarArbolController {

    @FXML
    private Button btnArbol;

    @FXML
    private ComboBox<String> comboArboles;

    private TreeService treeService;

    // Inyección manual del servicio
    public void setTreeService(TreeService treeService) {
        this.treeService = treeService;
        cargarArboles();
    }

    private void cargarArboles() {
        if (treeService != null) {
            comboArboles.getItems().setAll(treeService.getTreeNames());

            if (treeService.getNombreArbolActual() != null) {
                comboArboles.setValue(treeService.getNombreArbolActual());
            }
        }

        // Evento al cambiar la selección en el ComboBox
        comboArboles.setOnAction(e -> {
            String seleccionado = comboArboles.getValue();
            if (seleccionado != null && treeService != null) {
                treeService.setNombreArbolActual(seleccionado);
            }
        });
    }

    @FXML
    private void LimpiarArbolActual(ActionEvent event) {
        String arbolSeleccionado = comboArboles.getValue();

        if (arbolSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Por favor, seleccione un árbol primero.");
            return;
        }

        // Crear alerta de confirmación
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Acción");
        confirmacion.setHeaderText("¿Desea limpiar el árbol?");
        confirmacion.setContentText("¿Está seguro de que desea eliminar todos los nodos de '" + arbolSeleccionado + "'?");

        Optional<ButtonType> respuesta = confirmacion.showAndWait();

        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            try {
                treeService.setNombreArbolActual(arbolSeleccionado);
                treeService.limpiarArbolActual();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "El árbol '" + arbolSeleccionado + "' ha sido limpiado.");
            } catch (NoTreeSelectedException e) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró el árbol seleccionado.");
            }
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}