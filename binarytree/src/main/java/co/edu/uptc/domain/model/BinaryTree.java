package co.edu.uptc.domain.model;
import co.edu.uptc.domain.exception.DuplicateValueException;
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
   
    public void insert(int value) {
        root = insertRec(root, value);
    }

    private Node insertRec(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.getValue()) {
            current.setLeft(insertRec(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRec(current.getRight(), value));
        } else {
            throw new DuplicateValueException(value);
        }
        return current;
    }

    public boolean contains(int value) {
        return containsRec(root, value);
    }

    private boolean containsRec(Node current, int value) {
        if (current == null) return false;
        if (value == current.getValue()) return true;
        if (value < current.getValue()) return containsRec(current.getLeft(), value);
        return containsRec(current.getRight(), value);
    }

    public boolean isEmpty() {
        return root == null;
    }

}