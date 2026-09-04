package co.edu.uptc.domain.model;
import javafx.scene.Node;

public class BinaryTree {
    private String name;
    private Node root;
    
    /**Constructor vacío para poder usar el repositorio Json */
    public BinaryTree() {
    }

    /**Método constructor principal de la clase
     * 
     * @param name nombre del árbol
     * @param root  nodo raíz del árbol
     */
    public BinaryTree(String name, Node root) {
        this.name = name;
        this.root = root;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
    
}
