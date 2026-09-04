package co.edu.uptc.domain.model;

public class BinaryTree {
    private Node root;
    private String name;

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

}
