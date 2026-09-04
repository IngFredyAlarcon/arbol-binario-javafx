package co.edu.uptc.infraestructure.persistence;

import java.util.List;

import co.edu.uptc.domain.model.BinaryTree;

public interface BinaryTreeRepository {
    void saveAll( List<BinaryTree> treeList);

    List<BinaryTree> findAll();

     
}
