package co.edu.uptc.infraestructure.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import co.edu.uptc.domain.model.BinaryTree;

public class JsonRepository implements BinaryTreeRepository {
    private String pathname;
    private Gson gson;
    
    public JsonRepository(String pathname) {
        String rutaDeEjecucion = System.getProperty("user.dir");
        
        File carpetaData = new File(rutaDeEjecucion, "data");
        
        // Si la carpeta "data" no existe junto al programa, la crea
        if (!carpetaData.exists()) {
            carpetaData.mkdirs(); 
        }
        
        // Une la carpeta "data" con el nombre del archivo
        File archivoFinal = new File(carpetaData, pathname);
        
        // Guardamos la ruta completa
        this.pathname = archivoFinal.getAbsolutePath();
        
        this.gson = new GsonBuilder()
                        .serializeNulls()
                        .setPrettyPrinting()
                        .create();
    }

    @Override
    public void saveList(Map<String, BinaryTree> trees) {
        if (trees == null) return;

        try (FileWriter writer = new FileWriter(pathname)) {
            gson.toJson(trees, writer);
            System.out.println("Archivo guardado con éxito en: " + pathname); 
        } catch (Exception e) {
            System.err.println("Error al escribir: " + e.getMessage());
        }
    }
    @Override
    public Map<String, BinaryTree> loadList() {
        File archivo = new File(pathname);
        if (!archivo.exists()) {
            return new HashMap<>();
        }

        try (FileReader reader = new FileReader(archivo)) {
            Type mapType = new TypeToken<Map<String, BinaryTree>>() {}.getType();
            Map<String, BinaryTree> trees = gson.fromJson(reader, mapType);
            return trees != null ? trees : new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
