package co.edu.uptc.infraestructure.persistence;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import co.edu.uptc.domain.model.BinaryTree;
/**Clase JsonRepository que se encarga de envíar una lista de árboles almacenados en un archivo tipo JSON
 * y de escribir en un archivo Json una lista de árboles para almacenarla
 * 
 * 
 */
public class JsonRepository implements BinaryTreeRepository{
    private Gson gson;
    private String fileName;
    public JsonRepository(String fileName){
        String rutaDeEjecucion = System.getProperty("user.dir");
        File carpetaData = new File(rutaDeEjecucion, "../data");
        if (!carpetaData.exists()) {
            carpetaData.mkdirs(); 
        }
        // Une la carpeta "data" con el nombre del archivo (ej: "Admin.json") para tener el directorio correcto
        File archivoFinal = new File(carpetaData, fileName);
        this.fileName = archivoFinal.getAbsolutePath();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    @Override
    public List<BinaryTree> findAll() {
       
        return  null;
    }
    @Override
    /**Método que se encarga de almacenar la lista de árboles en archivo JSON
     * 
     * @param BinaryTree Nuevo árbol que se va a almacenar 
     */
    public void saveAll(List<BinaryTree> treeList) {
        if(treeList==null){
            treeList= new ArrayList<>();
        }
            try(FileWriter writer= new FileWriter(fileName)) {
                gson.toJson(treeList,writer);
            } catch (Exception e) {
                System.err.println("Error al escribir en el archivo json "+fileName+" :"+e.getMessage());
            }
        
    }
    

    
    
}
