package co.edu.uptc.domain.model;

public class BinaryTree {
    private Node root;
    private String name;
    
    public BinaryTree() {
    }


    public BinaryTree(String name) {
        this.name = name;
        root = null;
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
