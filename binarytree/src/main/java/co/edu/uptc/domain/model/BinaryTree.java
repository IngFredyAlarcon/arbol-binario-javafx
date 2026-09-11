package co.edu.uptc.domain.model;

import co.edu.uptc.domain.exception.DuplicateValueException;
import co.edu.uptc.domain.exception.ValueNotFoundException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

    public List<Integer> getPreOrder() {
        List<Integer> result = new ArrayList<>();
        preOrder(root, result);
        return result;
    }

    private void preOrder(Node node, List<Integer> result) {
        if (node != null) {
            result.add(node.getValue());
            preOrder(node.getLeft(), result);
            preOrder(node.getRight(), result);
        }
    }

    public List<Integer> getInOrder() {
        List<Integer> result = new ArrayList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(Node node, List<Integer> result) {
        if (node != null) {
            inOrder(node.getLeft(), result);
            result.add(node.getValue());
            inOrder(node.getRight(), result);
        }
    }

    public List<Integer> getPostOrder() {
        List<Integer> result = new ArrayList<>();
        postOrder(root, result);
        return result;
    }

    private void postOrder(Node node, List<Integer> result) {
        if (node != null) {
            postOrder(node.getLeft(), result);
            postOrder(node.getRight(), result);
            result.add(node.getValue());
        }
    }

    public List<Integer> getLevelOrder() {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.add(current.getValue());

            if (current.getLeft() != null) queue.add(current.getLeft());
            if (current.getRight() != null) queue.add(current.getRight());
        }

        return result;
    }
}   
