package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    private Node root;
    private String name;

    public BinaryTree(String name) {
        this.name = name;
        this.root = null;
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

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }
        if (value < current.getValue()) {
            current.setLeft(insertRecursive(current.getLeft(), value));
        } else if (value > current.getValue()) {
            current.setRight(insertRecursive(current.getRight(), value));
        } else {
            throw new DuplicateValueException(value);
        }
        return current;
    }
    public Node searchNode(int value) {
        return searchNodeRecursive(root, value);
    }
    
    private Node searchNodeRecursive(Node current, int value) {
        if (current == null) {
            throw new ValueNotFoundException(value);
        }
        if (value == current.getValue()) {
            return current;
        }
        if (value < current.getValue()) {
            return searchNodeRecursive(current.getLeft(), value);
        }
        return searchNodeRecursive(current.getRight(), value);
    }

}
