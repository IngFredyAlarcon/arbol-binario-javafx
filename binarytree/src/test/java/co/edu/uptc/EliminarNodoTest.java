package co.edu.uptc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.domain.model.Node;

public class EliminarNodoTest {

    @Test
    public void eliminarRaiz() {
        BinaryTree arbol = crearArbol(50, 30, 70);
        arbol.delete(50);
        assertEquals(List.of(30, 70), inOrden(arbol));
    }

    @Test
    public void eliminarHoja() {
        BinaryTree arbol = crearArbol(50, 30, 70);
        arbol.delete(70);
        assertEquals(List.of(30, 50), inOrden(arbol));
    }

    @Test
    public void eliminarNodoConDosHijos() {
        BinaryTree arbol = crearArbol(50, 30, 70, 20, 40);
        arbol.delete(30);
        assertEquals(List.of(20, 40, 50, 70), inOrden(arbol));
    }

    @Test
    public void eliminarNodoConUnHijo() {
        BinaryTree arbol = crearArbol(50, 30, 70, 20);
        arbol.delete(30);
        assertEquals(List.of(20, 50, 70), inOrden(arbol));
    }

    private BinaryTree crearArbol(int... valores) {
        BinaryTree arbol = new BinaryTree("ArbolTest");
        for (int valor : valores) {
            arbol.insert(valor);
        }
        return arbol;
    }

    private List<Integer> inOrden(BinaryTree arbol) {
        List<Integer> resultado = new ArrayList<>();
        inOrdenRecursivo(arbol.getRoot(), resultado);
        return resultado;
    }

    private void inOrdenRecursivo(Node nodo, List<Integer> resultado) {
        if (nodo == null) {
            return;
        }
        inOrdenRecursivo(nodo.getLeft(), resultado);
        resultado.add(nodo.getValue());
        inOrdenRecursivo(nodo.getRight(), resultado);
    }
}