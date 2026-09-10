package co.edu.uptc.infraestructure.persistence;
import java.util.List;
import java.util.Map;

import co.edu.uptc.domain.model.BinaryTree;
public interface BinaryTreeRepository {
     
    void saveList(Map<String, BinaryTree> trees);

    Map<String, BinaryTree> loadList();
    

    void save(String name, BinaryTree tree);
    BinaryTree findByName(String name);
    List<String> findAll();
    void delete(String name);
    boolean exists(String name);
}