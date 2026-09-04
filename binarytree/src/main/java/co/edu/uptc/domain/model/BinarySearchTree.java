package co.edu.uptc.domain.model;


import java.util.ArrayList;
import java.util.List;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

public class BinarySearchTree {

    private Node root;

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

    public boolean contains(int value) {
        return containsRecursive(root, value);
    }

    private boolean containsRecursive(Node current, int value) {

        if (current == null) {
            return false;
        }

        if (value == current.getValue()) {
            return true;
        }

        if (value < current.getValue()) {
            return containsRecursive(current.getLeft(), value);
        }

        return containsRecursive(current.getRight(), value);
    }

    public void delete(int value) {

        if (!contains(value)) {
            throw new ValueNotFoundException(value);
        }

        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {

        if (current == null) {
            return null;
        }

        if (value < current.getValue()) {
            current.setLeft(deleteRecursive(current.getLeft(), value));

        } else if (value > current.getValue()) {
            current.setRight(deleteRecursive(current.getRight(), value));

        } else {

            // Caso 1: nodo hoja
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }

            // Caso 2: solo hijo derecho
            if (current.getLeft() == null) {
                return current.getRight();
            }

            // Caso 2: solo hijo izquierdo
            if (current.getRight() == null) {
                return current.getLeft();
            }

            // Caso 3: dos hijos
            int successorValue = findMin(current.getRight());
            Node replacement = new Node(successorValue);

            replacement.setLeft(current.getLeft());
            replacement.setRight(
                    deleteRecursive(current.getRight(), successorValue)
            );

            return replacement;
        }

        return current;
    }

    public List<Integer> inOrder() {

        List<Integer> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    private void inOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        inOrderRecursive(current.getLeft(), result);
        result.add(current.getValue());
        inOrderRecursive(current.getRight(), result);
    }

    public List<Integer> preOrder() {

        List<Integer> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    private void preOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        result.add(current.getValue());
        preOrderRecursive(current.getLeft(), result);
        preOrderRecursive(current.getRight(), result);
    }

    public List<Integer> postOrder() {

        List<Integer> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    private void postOrderRecursive(Node current, List<Integer> result) {

        if (current == null) {
            return;
        }

        postOrderRecursive(current.getLeft(), result);
        postOrderRecursive(current.getRight(), result);
        result.add(current.getValue());
    }

    public int size() {
        return countNodes(root);
    }

    private int countNodes(Node current) {

        if (current == null) {
            return 0;
        }

        return 1
                + countNodes(current.getLeft())
                + countNodes(current.getRight());
    }

    public int height() {
        return heightRecursive(root);
    }

    private int heightRecursive(Node current) {

        if (current == null) {
            return -1;
        }

        return 1 + Math.max(
                heightRecursive(current.getLeft()),
                heightRecursive(current.getRight())
        );
    }

    public int countLeaves() {
        return countLeavesRecursive(root);
    }

    private int countLeavesRecursive(Node current) {

        if (current == null) {
            return 0;
        }

        if (current.getLeft() == null && current.getRight() == null) {
            return 1;
        }

        return countLeavesRecursive(current.getLeft())
                + countLeavesRecursive(current.getRight());
    }

    public int findMin() {

        if (isEmpty()) {
            throw new ValueNotFoundException(
                    Integer.MIN_VALUE
            );
        }

        return findMin(root);
    }

    private int findMin(Node current) {

        if (current.getLeft() == null) {
            return current.getValue();
        }

        return findMin(current.getLeft());
    }

    public int findMax() {

        if (isEmpty()) {
            throw new ValueNotFoundException(
                    Integer.MAX_VALUE
            );
        }

        return findMax(root);
    }

    private int findMax(Node current) {

        if (current.getRight() == null) {
            return current.getValue();
        }

        return findMax(current.getRight());
    }

    public void clear() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }
} 