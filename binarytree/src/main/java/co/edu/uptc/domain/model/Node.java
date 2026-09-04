package co.edu.uptc.domain.model;

public class Node {

    private int value;
    private Node left;
    private Node right;

    
    //Constructor vacío para hacer uso del repositorío Json
    public Node() {
    }

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}