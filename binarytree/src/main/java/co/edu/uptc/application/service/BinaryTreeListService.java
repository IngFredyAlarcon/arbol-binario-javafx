package co.edu.uptc.application.service;
import java.util.List;
import co.edu.uptc.domain.model.BinaryTree;
import co.edu.uptc.infraestructure.persistence.BinaryTreeRepository;
/**Clase encargada de manejar la LISTA de árboles, con los distintos métodos de CRUD
 * Crear
 * Leer
 * Actualizar
 * Eliminar
 * Trabaja con una lista en memoria que proviene de una lista del repositorio
 * Al finalizar el programa se enviará la lista con los cambios al repositorio
 */
public class BinaryTreeListService {
    
    private BinaryTreeRepository repository;
    private  List<BinaryTree> treeList;

    /**Método constructor de la lista
     * 
     * @param repository Un repositorio génerico, que se encarga de almacenar la lista de árboles
     * Con este método también se envía la lista desde el repositorio y se guarda en memoria 
     */
    public BinaryTreeListService(BinaryTreeRepository repository) {
        this.repository = repository;
        this.treeList = repository.findAll();
    }
    /**Método que envía la lista en memoria al repositorio
     * 
     */
    public void sendListToRepository(){
        repository.saveAll(treeList);
    }

    

    

}
