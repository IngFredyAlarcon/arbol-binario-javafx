package co.edu.uptc.application.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.exception.NoTreeSelectedException;
import co.edu.uptc.domain.model.BinarySearchTree;

public class TreeService {
    private final List<String> treeNames = new ArrayList<>();
    private final List<BinarySearchTree> treeList = new ArrayList<>();
    private String nombreArbolActual;

   public void limpiarArbolActual() {
    if (nombreArbolActual == null) {
        throw new NoTreeSelectedException();
    }

    int indice = treeNames.indexOf(nombreArbolActual);
    if (indice == -1) {
        throw new NoTreeSelectedException();
    }

    treeList.get(indice).clear();
}

public List<String> getTreeNames() {
        return treeNames;
    }

    public String getNombreArbolActual() {
        return nombreArbolActual;
    }

    public void setNombreArbolActual(String nombreArbolActual) {
        this.nombreArbolActual = nombreArbolActual;
    }
}
