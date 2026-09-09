package co.edu.uptc.presentation;

import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Responsable únicamente de dibujar un BinaryTree dentro de un Pane de JavaFX.
 */
public class TreeDrawer {

    private static final double RADIO_NODO = 20;
    private static final double ESPACIADO_X = 55;
    private static final double ESPACIADO_Y = 80;
    private static final double MARGEN_X = 40;
    private static final double MARGEN_Y = 40;

    private int contadorX;

    public void draw(BinaryTree tree, Pane pane) {
        pane.getChildren().clear();
        contadorX = 0;

        if (tree == null || tree.isEmpty()) {
            pane.setPrefWidth(0);
            pane.setPrefHeight(0);
            return;
        }

        Map<Node, double[]> posiciones = new HashMap<>();
        calcularPosiciones(tree.getRoot(), 0, posiciones);

        dibujarLineas(tree.getRoot(), posiciones, pane);
        dibujarNodos(tree.getRoot(), posiciones, pane);

        ajustarTamanoPane(pane, posiciones);
    }

    private void calcularPosiciones(Node nodo, int profundidad, Map<Node, double[]> posiciones) {
        if (nodo == null) return;

        calcularPosiciones(nodo.getLeft(), profundidad + 1, posiciones);

        double x = MARGEN_X + contadorX * ESPACIADO_X;
        double y = MARGEN_Y + profundidad * ESPACIADO_Y;
        posiciones.put(nodo, new double[]{x, y});
        contadorX++;

        calcularPosiciones(nodo.getRight(), profundidad + 1, posiciones);
    }

    private void dibujarLineas(Node nodo, Map<Node, double[]> posiciones, Pane pane) {
        if (nodo == null) return;

        double[] posPadre = posiciones.get(nodo);

        if (nodo.getLeft() != null) {
            double[] posHijo = posiciones.get(nodo.getLeft());
            Line linea = new Line(posPadre[0], posPadre[1], posHijo[0], posHijo[1]);
            linea.getStyleClass().add("tree-line");
            pane.getChildren().add(linea);
        }
        if (nodo.getRight() != null) {
            double[] posHijo = posiciones.get(nodo.getRight());
            Line linea = new Line(posPadre[0], posPadre[1], posHijo[0], posHijo[1]);
            linea.getStyleClass().add("tree-line");
            pane.getChildren().add(linea);
        }

        dibujarLineas(nodo.getLeft(), posiciones, pane);
        dibujarLineas(nodo.getRight(), posiciones, pane);
    }

    private void dibujarNodos(Node nodo, Map<Node, double[]> posiciones, Pane pane) {
        if (nodo == null) return;

        double[] pos = posiciones.get(nodo);

        Circle circulo = new Circle(pos[0], pos[1], RADIO_NODO);
        circulo.getStyleClass().add("tree-node");
        circulo.setUserData(nodo.getValue());
        Text texto = new Text(String.valueOf(nodo.getValue()));
        texto.getStyleClass().add("node-text");
        texto.setX(pos[0] - (String.valueOf(nodo.getValue()).length() * 4.0));
        texto.setY(pos[1] + 4);

        pane.getChildren().addAll(circulo, texto);

        dibujarNodos(nodo.getLeft(), posiciones, pane);
        dibujarNodos(nodo.getRight(), posiciones, pane);
    }

    private void ajustarTamanoPane(Pane pane, Map<Node, double[]> posiciones) {
        double maxX = 0;
        double maxY = 0;
        for (double[] pos : posiciones.values()) {
            maxX = Math.max(maxX, pos[0]);
            maxY = Math.max(maxY, pos[1]);
        }
        pane.setPrefWidth(maxX + MARGEN_X + RADIO_NODO);
        pane.setPrefHeight(maxY + MARGEN_Y + RADIO_NODO);
    }

    public void highlightNode(Pane pane, int targetValue) {
        resetNodeStyles(pane); 

        for (javafx.scene.Node element : pane.getChildren()) {
            if (element instanceof Circle) {
                Circle circulo = (Circle) element;
                if (circulo.getUserData() != null && (int) circulo.getUserData() == targetValue) {
                    circulo.setStyle("-fx-fill: #f1c40f; -fx-stroke: #d35400; -fx-stroke-width: 3.5;");
                    break;
                }
            }
        }
    }
    public void resetNodeStyles(Pane pane) {
        for (javafx.scene.Node element : pane.getChildren()) {
            if (element instanceof Circle) {
                Circle circulo = (Circle) element;
                circulo.setStyle(""); // Elimina los estilos inline
            }
        }
    }
}
