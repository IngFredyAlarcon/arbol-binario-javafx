package co.edu.uptc.infraestructure.persistence;

import java.util.Map;

import co.edu.uptc.domain.model.BinaryTree;

public interface BinaryTreeRepository {

    //Interfaz para guardar los árboles en el archivo
    void saveList(Map<String, BinaryTree> trees);

    /**
     * Para leer del archivo de persistencia todos los árboles
     * previamente guardados.
     *
     * @return un mapa con los árboles almacenados (nombre -> árbol),
     *         o un mapa vacío si no hay datos guardados aún.
     */
    Map<String, BinaryTree> loadList();

}