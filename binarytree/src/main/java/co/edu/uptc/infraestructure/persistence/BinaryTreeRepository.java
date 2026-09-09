package co.edu.uptc.infraestructure.persistence;
import java.util.Map;

import co.edu.uptc.domain.model.BinaryTree;
public interface BinaryTreeRepository {
    //Interfaz para guardar los 
    void saveList(Map<String, BinaryTree> trees);

    Map<String, BinaryTree> loadList();
    
}

