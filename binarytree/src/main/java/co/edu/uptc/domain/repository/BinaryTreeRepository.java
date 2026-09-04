package co.edu.uptc.domain.repository;

import co.edu.uptc.domain.model.BinarySearchTree;

import java.util.List;

public interface BinaryTreeRepository {

    void save(String name, BinarySearchTree tree);

    BinarySearchTree findByName(String name);

    List<String> findAll();

    void delete(String name);

    boolean exists(String name);
}
